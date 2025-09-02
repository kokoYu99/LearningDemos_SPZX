package com.koko.spzx.exception;

import com.koko.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 全局业务异常基类
 */
@Data
@EqualsAndHashCode(callSuper = true) // callSuper=true会包含父类的属性
public class BusinessException extends RuntimeException {

    // 封装业务状态码枚举
    private final ResultCodeEnum resultCodeEnum;

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        // 使用枚举中的消息作为异常的message
        super(resultCodeEnum.getMessage());
        this.resultCodeEnum = resultCodeEnum;
    }
}