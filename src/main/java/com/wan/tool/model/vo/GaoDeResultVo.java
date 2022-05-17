package com.wan.tool.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月07日 15:20:47
 */
@Data
public class GaoDeResultVo {

    private String status;

    private String info;

    private String count;

    private List<Geocodes> geocodes;

}
