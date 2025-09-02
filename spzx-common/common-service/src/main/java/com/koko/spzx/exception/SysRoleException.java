package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/* 自定义的角色修改异常 */
public class SysRoleException  extends BusinessException {
    public SysRoleException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}