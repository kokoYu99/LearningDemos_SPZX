package com.koko.spzx.service.impl;

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

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
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

    private boolean isPasswordValid(String inputPassword, String dbPassword) {
        //将输入的密码进行md5转换
        String encryptedInput = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //返回校验结果
        return encryptedInput.equals(dbPassword);
    }

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
