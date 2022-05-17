package com.wan.tool.config;

import com.wan.tool.model.vo.Idm2Token;
import com.wan.tool.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月15日 20:48:02
 */
@Component
public class IdmConfig {

    @Resource
    private IdmProperties idmProperties;

    @Resource
    private RestTemplateExecutor restTemplateExecutor;

    @Value("${idm.token.url:https://www.ziyun-cloud.com/nodeapi/idmhierarchy/getToken}")
    private String idmTokenUrl;

    /**
     * 功能描述：
     * -- 获取idm2 accessToken
     *
     * @author : wan
     * @return : java.lang.String
     * @since  : 2022/5/15 下午9:08
     */
    public String idm2Token() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("clientId", idmProperties.getClientId());
        paramMap.put("clientSecret", idmProperties.getClientSecret());
        paramMap.put("userName", idmProperties.getUserName());
        paramMap.put("password", idmProperties.getPassword());
        paramMap.put("grantType", idmProperties.getGrantType());
        String json = restTemplateExecutor.post(idmTokenUrl, paramMap, MediaType.APPLICATION_JSON);
        Idm2Token idm2Token = JsonUtil.json2Object(json, Idm2Token.class);
        return idm2Token.getAccessToken();
    }

}
