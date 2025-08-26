package com.koko.spzx.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.koko.spzx.model.vo.system.ValidateCodeVo;
import com.koko.spzx.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    public ValidateCodeServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ValidateCodeVo generateValiCode() {

        //1. 使用hutool工具生成验证码和验证码图片
        //参数：宽，高，验证码位数，干扰线数量
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String captchaCode = captcha.getCode();
        String imageBase64 = captcha.getImageBase64();


        //2. 将验证码存入redis中。key为唯一值，使用uuid；value为验证码
        String redis_key = "user:login:captcha:" + UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(redis_key, captchaCode, 5, TimeUnit.MINUTES);

        //3. 构建vo对象，包含验证码在redis中的key、验证码图片，返回
        ValidateCodeVo vo = new ValidateCodeVo();
        vo.setCodeKey(redis_key);
        vo.setCodeValue("data:image/png;base64," + imageBase64);
        return vo;
    }
}
