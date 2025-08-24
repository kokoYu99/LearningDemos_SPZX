package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/* 自定义的角色修改异常 */
@Data
public class SysRoleException extends RuntimeException {

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public SysRoleException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public SysRoleException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}