package com.koko.spzx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.util.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class AuthContextInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 从请求头中获取请求方式。
        //如果是预检请求，直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        //2. 非预检请求，从请求头中获取token，判断是否存在
        String token = request.getHeader("token");
        //如果token为空，响应208错误码，并返回false，要求重新登录
        if (StrUtil.isEmpty(token)) {
            responseNotLoginInfo(response);
            return false;
        }

        //3. token存在，去redis获取用户信息
        //如果用户信息为空(get key = null|"")，响应208错误码，并返回false，要求重新登录
        String sysUserInfo = redisTemplate.opsForValue().get("user:login:" + token);
        if (StrUtil.isEmpty(sysUserInfo)) {
            responseNotLoginInfo(response);
            return false;
        }

        //4. 用户信息存在，将其转换为java对象，并存入threadLocal对象中，并设置过期时间
        SysUser sysUser = JSON.parseObject(sysUserInfo, SysUser.class);
        AuthContextUtil.set(sysUser);
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);

        return true;
    }

    /* 日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(AuthContextInterceptor.class);

    /* 未登录的响应 */
    private void responseNotLoginInfo(HttpServletResponse response) {
        //1. 构建响应结果
        Result result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);

        //2. 设置响应的编码格式
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //3. 获取输出流，输出响应结果
        try (PrintWriter writer = response.getWriter()) {
            //将响应结果转换为json字符串，并输出
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            //日志输出异常
            logger.error("fail to send response for unauthorized request", e);
        }

    }


    /* 完成所有请求后，清空threadLocal中的数据 */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }
}
