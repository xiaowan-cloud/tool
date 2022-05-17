package com.wan.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wan
 * @version 1.0.0
 * @Description restTTemplate配置类
 * @createTime 2021年11月26日 14:57:18
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateExecuttor() {
        return new RestTemplate();
    }

}
