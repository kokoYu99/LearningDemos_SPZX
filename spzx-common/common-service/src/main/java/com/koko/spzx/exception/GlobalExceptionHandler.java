package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //包含 @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

    /* 处理登录异常 */
    @ExceptionHandler(LoginException.class)
    public Result loginExceptionHandler(LoginException e) {
        //从方法参数中获取异常对象
        //后端打印异常码和异常信息
        System.out.println(e.getCode() + " : " + e.getMessage());

        //返回统一结果对象，封装异常对象中的ResultCodeEnum
        return Result.build(null, e.getResultCodeEnum());
    }

    /*处理其他异常。兜个底*/
    @ExceptionHandler(Exception.class)
    public Result handleRuntimeExceptionHandler(RuntimeException e) {
        System.out.println(e.getMessage());
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }


}
