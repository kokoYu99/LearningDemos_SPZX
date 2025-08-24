package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class SysUserException extends RuntimeException{

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public SysUserException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public SysUserException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
