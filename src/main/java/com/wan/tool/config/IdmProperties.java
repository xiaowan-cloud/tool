package com.wan.tool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: finance
 * @description: idm2
 * @author: YXZ
 * @create: 2021-03-04 14:23
 **/
@Component
@ConfigurationProperties(prefix = "idm2")
@Data
public class IdmProperties {

    private String url = "https://www.ziyun-cloud.com/kong/idm";

    private String clientId;

    private String clientSecret;

    private String userName;

    private String password;

    private String grantType;

    private String ssoId;

}

