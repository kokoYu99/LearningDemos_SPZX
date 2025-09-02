package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/* 自定义的菜单异常 */
public class SysMenuException extends BusinessException {
    public SysMenuException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}