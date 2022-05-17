package com.wan.tool.model.enums;

import io.swagger.annotations.ApiModel;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月28日 17:29:12
 */
@ApiModel("邮件类型枚举")
public enum EmailTypeEnum {

    /**
     * 收件人
     */
    TO("TO", "收件人"),
    /**
     * 抄送人
     */
    CC("CC", "抄送人"),
    /**
     * 密送人
     */
    BCC("BCC", "密送人");

    private String type;

    private String value;

    EmailTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public final String getType() {
        return this.type;
    }

    public final String getValue() {
        return this.value;
    }

}
