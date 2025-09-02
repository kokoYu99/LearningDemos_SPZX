package com.koko.spzx.exception;


import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 自定义的登录异常 */

public class LoginException extends BusinessException {
    public LoginException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}
