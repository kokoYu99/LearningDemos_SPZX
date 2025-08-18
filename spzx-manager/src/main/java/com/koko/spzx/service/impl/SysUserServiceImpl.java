package com.koko.spzx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.koko.spzx.exception.LoginException;
import com.koko.spzx.mapper.SysUserMapper;
import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.system.LoginVo;
import com.koko.spzx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /* 登录 */
    @Override
    public LoginVo login(LoginDto loginDto) {

        //先校验验证码，校验通过，再校验用户名和密码
        isCaptchaValid(loginDto);

        //1. 从传入的dto中取出username，去数据库查询user对象
        SysUser dbSysUser = mapper.findUserByUsername(loginDto.getUsername());

        //2. 判断查询到的user对象是否为null

        //2.1 如果user对象为null，或者密码错误，抛异常"用户名或密码错误"
        if (dbSysUser == null || !isPasswordValid(loginDto.getPassword(), dbSysUser.getPassword())) {
            //TODO 后续改为业务异常，由异常处理类统一处理
            throw new LoginException(ResultCodeEnum.LOGIN_ERROR);
        }

        //2.2 密码正确，生成token，存入redis设置timeout，存入vo对象并返回
        return buildLoginVo(dbSysUser);


    }

    /* 登录后，获取用户信息 */
    @Override
    public SysUser getUserInfo(String token) {

        //根据token，从redis获取用户信息
        String userInfo = redisTemplate.opsForValue().get("user:login:" + token);

        //将用户信息的json转换为对象。使用fastjson工具
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);

        //对象转换成功，再返回给前端
        if (sysUser != null) {
            //要将密码置空
            sysUser.setPassword("");
            return sysUser;
        }

        throw new LoginException(ResultCodeEnum.DATA_ERROR);

    }

    /* 退出登录 */
    @Override
    public void logout(String token) {
        //删除redis中的用户信息
        Boolean delete = redisTemplate.delete("user:login:" + token);

        //如果删除失败，抛异常
        if (Boolean.FALSE.equals(delete)) {
            throw new LoginException(ResultCodeEnum.LOGOUT_ERROR);
        }
    }

    /* 校验验证码 */
    private void isCaptchaValid(LoginDto dto) {
        //1. 获取用户输入的验证码
        String input_captcha = dto.getCaptcha();

        //2. 获取验证码在redis中对应的key
        String redis_key = dto.getCodeKey();

        //3. 从redis中获取验证码
        String redis_captcha = redisTemplate.opsForValue().get(redis_key);

        //比较验证码。如果redis中无此验证码，或与输入的不匹配，抛异常
        if (redis_captcha == null || !input_captcha.equalsIgnoreCase(redis_captcha)) {
            //验证码错误异常
            throw new LoginException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //如果验证码匹配，将其从redis中删除，节省空间
        redisTemplate.delete(redis_key);

    }

    /* 校验密码 */
    private boolean isPasswordValid(String inputPassword, String dbPassword) {
        //将输入的密码进行md5转换
        String encryptedInput = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //返回校验结果
        return encryptedInput.equals(dbPassword);
    }

    /* 构建结果对象vo */
    private LoginVo buildLoginVo(SysUser dbSysUser) {
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");

        //存入redis
        //key: "user:login:token"，value: 用户信息
        //过期时间 30min
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(dbSysUser), 30, TimeUnit.MINUTES);

        //创建vo对象，存入token
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        //返回vo对象
        return loginVo;
    }

}
