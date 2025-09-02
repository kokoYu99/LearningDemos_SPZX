package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j //生成日志对象log
public class GlobalExceptionHandler {

    // 通过 @Value 注解注入当前激活的 Spring Profile
    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    // 定义开发环境的 profile 名称
    private static final List<String> DEV_PROFILES = Arrays.asList("dev", "test");

    /**
     * 统一处理所有自定义的业务异常 (BusinessException)
     * 通过继承，这个方法可以捕获 LoginException, SysRoleException 等所有子类异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        // 重点优化1：使用 SLF4J 的参数化日志，传入整个异常对象 e
        // 这样日志框架会自动打印完整的堆栈信息，方便排查
        log.error("捕获到业务异常: code={}, message='{}'", e.getResultCodeEnum().getCode(), e.getMessage(), e);

        // 返回给前端的依然是封装好的、明确的业务错误信息
        return Result.build(null, e.getResultCodeEnum());
    }

    /**
     * 兜底处理所有未被捕获的“未知”异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleUncaughtException(Exception e) {
        // 重点优化1：同样记录完整的未知异常堆栈
        log.error("捕获到系统未知异常: ", e);

        // 重点优化2：区分环境返回错误信息
        // 如果是开发或测试环境，返回详细的堆栈信息，方便前端联调和快速定位问题
        if (DEV_PROFILES.contains(activeProfile)) {
            // 为了不破坏原有 Result 结构，可以将详细信息放在 message 中返回
            // 建议在 Result.build 方法中增加一个重载，方便传入自定义的 message
            String detailedMessage = "服务器内部错误: " + e.getMessage();
            log.error(detailedMessage);
            return Result.build(null, ResultCodeEnum.SYSTEM_ERROR.getCode(), detailedMessage);
        }

        // 如果是生产环境，出于安全考虑，返回统一、模糊的错误提示
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }
}