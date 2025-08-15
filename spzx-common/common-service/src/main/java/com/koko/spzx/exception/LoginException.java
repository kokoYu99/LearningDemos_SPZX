package com.koko.spzx.exception;


import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/* 自定义的登录异常 */
@Data
public class LoginException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public LoginException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public LoginException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
