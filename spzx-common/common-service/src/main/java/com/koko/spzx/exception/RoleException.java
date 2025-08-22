package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/* 自定义的角色修改异常 */
@Data
public class RoleException extends RuntimeException {

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public RoleException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public RoleException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}