package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //包含 @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /* 处理登录异常 */
    @ExceptionHandler(LoginException.class)
    public Result handleLoginException(LoginException e) {
        //从方法参数中获取异常对象
        //后端打印异常码和异常信息
        logger.error("捕获到登录异常：" + e);

        //返回统一结果对象，封装异常对象中的ResultCodeEnum
        return Result.build(null, e.getResultCodeEnum());
    }

    /* 处理角色异常 */
    @ExceptionHandler(SysRoleException.class)
    public Result handleRoleException(SysRoleException e) {
        //从方法参数中获取异常对象
        //后端打印异常码和异常信息
        logger.error("捕获到用户异常：" + e);

        //返回统一结果对象，封装异常对象中的ResultCodeEnum
        return Result.build(null, e.getResultCodeEnum());
    }

    /* 处理用户异常 */
    @ExceptionHandler(SysUserException.class)
    public Result handleSysUserException(SysUserException e) {
        //从方法参数中获取异常对象
        //后端打印异常码和异常信息
        logger.error("捕获到用户异常：" + e);

        //返回统一结果对象，封装异常对象中的ResultCodeEnum
        return Result.build(null, e.getResultCodeEnum());
    }

    /*处理其他异常。兜个底*/
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error("捕获到全局异常：" + e.getCause());
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }


}
