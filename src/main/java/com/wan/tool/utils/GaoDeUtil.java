package com.wan.tool.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.tool.config.RestTemplateExecutor;
import com.wan.tool.model.vo.GaoDeResultVo;
import com.wan.tool.model.vo.Geocodes;
import com.wan.tool.model.vo.LocaltionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月07日 15:10:19
 */
@Component
@Slf4j
public class GaoDeUtil {

    private final String KEY = "54c0ac33f091b90744e090a3e0e7b0af";

    private final String URL = "https://restapi.amap.com/v3/geocode/geo";

    @Resource
    private RestTemplateExecutor restTemplateExecutor;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 功能描述：
     * -- 获取地址经纬度
     *
     * @param address 地址
     * @return :
     * @author : wan
     * @date : 2022/3/7 15:26
     */
    public LocaltionVo getLatitudeLatitude(String address) {
        GaoDeResultVo gaoDeResultVo = null;
        try {
            Map<String, String> paramMap = new HashMap<>(5);
            paramMap.put("key", KEY);
            paramMap.put("address", address);
            String result = restTemplateExecutor.get(URL, paramMap);
            gaoDeResultVo = objectMapper.readValue(result, new TypeReference<GaoDeResultVo>(){});
            if (null == gaoDeResultVo || null == gaoDeResultVo.getGeocodes() || 0 == gaoDeResultVo.getGeocodes().size() || StringUtils.isBlank(gaoDeResultVo.getGeocodes().get(0).getLocation())) {
                return null;
            }
        }catch (Exception e) {
            log.error("发生异常:{}", e.getMessage());
            return null;
        }
        Geocodes geocode = gaoDeResultVo.getGeocodes().get(0);
        String location = geocode.getLocation();
        String[] split = location.split(",");
        if (split != null && split.length > 1) {
            LocaltionVo localtionVo = new LocaltionVo();
            localtionVo.setLongitude(split[0]);
            localtionVo.setLatitude(split[1]);
            return localtionVo;
        }
        return null;
    }

}
