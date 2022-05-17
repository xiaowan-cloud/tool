package com.wan.tool.model.vo;

import lombok.Data;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月15日 21:06:45
 */
@Data
public class Idm2Token {

    private String accessToken;

    private String tokenType;

    private String ssoId;

}
