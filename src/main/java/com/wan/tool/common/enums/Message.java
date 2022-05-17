package com.wan.tool.common.enums;

/**
 * @author wan
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年11月25日 11:52:21
 */
public enum Message {

    /**
     * 参数为空
     */
    PARAM_NOT_FOUND("参数为空"),

    /**
     * 参数错误
     */
    PARAM_ERROR("参数错误"),

    /**
     * 数据未找到
     */
    DATA_NOT_FOUND("数据未找到"),

    /**
     * 数据错误
     */
    DATA_ERROR("数据错误");

    String value;

    Message(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
