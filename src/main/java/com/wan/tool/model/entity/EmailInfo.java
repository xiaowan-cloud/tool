package com.wan.tool.model.entity;

import com.wan.tool.model.enums.EmailTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月28日 17:22:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("邮件实体")
public class EmailInfo {

    @ApiModelProperty(value = "邮件接收地址")
    private String receiveAccount;

    @ApiModelProperty(value = "发送类型：TO:收件人 CC:抄送人 BCC:密送人")
    private EmailTypeEnum type;

}
