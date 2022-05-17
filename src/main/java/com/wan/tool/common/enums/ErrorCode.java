package com.wan.tool.common.enums;

/**
 * @author wan
 * @version 1.0.0
 * @Description 状态码枚举
 * @createTime 2021年11月25日 11:42:39
 */
public enum ErrorCode {

    /**
     * 参数未找到
     */
    PARAM_NOT_FOUND(4001),

    /**
     * 参数错误
     */
    PARAM_ERROR(4002),

    /**
     * 数据未找到
     */
    DATA_NOT_FOUND(5003),

    /**
     * 数据错误
     */
    DATA_ERROR(5004);

    Integer value;

    ErrorCode(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
