package com.wan.tool.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月07日 15:12:08
 */
@Data
@ApiModel("地址实体")
@Accessors(chain = true)
public class LocaltionVo {

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("纬度")
    private String latitude;

}
