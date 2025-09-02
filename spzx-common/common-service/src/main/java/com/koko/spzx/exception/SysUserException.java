package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

public class SysUserException extends BusinessException {

    public SysUserException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}
