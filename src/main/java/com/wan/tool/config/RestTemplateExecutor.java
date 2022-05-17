package com.wan.tool.config;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * restTemplate执行器，简化restTemplate API
 *
 * @author xuwangshen
 */
@Slf4j
@Component
public class RestTemplateExecutor {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * get http request
     *
     * @param url
     * @param uriVariables uri参数
     * @param headers      request header参数
     * @param clazz        返回实体类class
     * @return
     */
    public <T> T get(String url, Map<String, ?> uriVariables, HttpHeaders headers, Class<T> clazz) {
        return request(url, null, uriVariables, headers, HttpMethod.GET, null, clazz);
    }

    /**
     * @param url
     * @param headers request header参数
     * @param clazz   返回实体类class
     * @param <T>
     * @return
     */
    public <T> T get(String url, HttpHeaders headers, Class<T> clazz) {
        return request(url, null, null, headers, HttpMethod.GET, null, clazz);
    }

    /**
     * get http request
     *
     * @param url
     * @param uriVariables uri参数
     * @param headers      request header参数
     * @return
     */
    public String get(String url, Map<String, ?> uriVariables, HttpHeaders headers) {
        return request(url, null, uriVariables, headers, HttpMethod.GET, null, String.class);
    }

    /**
     * get http request
     *
     * @param url
     * @param httpHeaders
     * @return
     */
    public String get(String url, HttpHeaders httpHeaders) {
        return request(url, null, null, httpHeaders, HttpMethod.GET, null, String.class);
    }

    /**
     * get http request
     *
     * @param url
     * @param uriVariables uri参数
     * @return
     */
    public String get(String url, Map<String, ?> uriVariables) {
        return request(url, null, uriVariables, null, HttpMethod.GET, null, String.class);
    }

    /**
     * post http request
     *
     * @param url
     * @param body      request body参数
     * @param mediaType
     * @return
     */
    public String post(String url, Map<String, ?> body, MediaType mediaType) {
        return request(url, body, null, new HttpHeaders(), HttpMethod.POST, mediaType, String.class);
    }

    /**
     * post http request
     *
     * @param url
     * @param body      request body参数
     * @param headers   request header参数
     * @param mediaType
     * @return
     */
    public String post(String url, Map<String, ?> body, HttpHeaders headers, MediaType mediaType) {
        return request(url, body, null, headers, HttpMethod.POST, mediaType, String.class);
    }

    /**
     * post http request
     *
     * @param url
     * @param headers request header参数
     * @return
     */
    public String post(String url, Map<String, ?> uriVariables, HttpHeaders headers) {
        return request(url, null, uriVariables, headers, HttpMethod.POST, null, String.class);
    }

    /**
     * put http request
     *
     * @param url
     * @param body      request body参数
     * @param headers   request header参数
     * @param mediaType
     * @return
     */
    public String put(String url, Map<String, ?> body, HttpHeaders headers, MediaType mediaType) {
        return request(url, body, null, headers, HttpMethod.PUT, mediaType, String.class);
    }

    public String put(String url, Map<String, ?> uriVariables, Map<String, ?> body, HttpHeaders headers, MediaType mediaType) {
        return request(url, body, uriVariables, headers, HttpMethod.PUT, mediaType, String.class);
    }
    /**
     * http request
     *
     * @param url
     * @param body          请求body
     * @param uriVariables  uri参数
     * @param headers       请求头信息
     * @param httpMethod
     * @param mediaType     ContentTypes
     * @param responseClazz 返回类型
     */
    public <T> T request(String url, Map<String, ?> body, Map<String, ?> uriVariables, HttpHeaders headers, HttpMethod httpMethod, MediaType mediaType, Class<T> responseClazz) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }
        if (log.isInfoEnabled()) {
            log.info("request method：{}", httpMethod);
            log.info("request url：{}", url);
            log.info("request header：{}", headers);
            log.info("request uriVariables：{}", uriVariables);
            log.info("request body：{}", body);
        }
        HttpEntity<?> requestEntity = (mediaType == MediaType.APPLICATION_JSON) ? new HttpEntity<>(new MappingJacksonValue(body), headers)
                : new HttpEntity<>(uriVariables, headers);
        ResponseEntity<T> responseEntity = uriVariables == null ? restTemplate.exchange(expandUrl(url, null), httpMethod, requestEntity, responseClazz)
                : restTemplate.exchange(expandUrl(url, uriVariables.keySet()), httpMethod, requestEntity, responseClazz, uriVariables);
        if (log.isInfoEnabled()) {
            log.info("response ：{}", responseEntity);
        }
        return responseEntity.getBody();
    }

    public String postFormData(String url, HttpHeaders headers, MultiValueMap<String, Object> map) throws URISyntaxException {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(
                map, headers);
        //发送请求，设置请求返回数据格式为String
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class, MediaType.MULTIPART_FORM_DATA);
        return responseEntity.getBody();
    }

    /**
     * @param url
     * @param keys
     * @return: String
     */
    private String expandUrl(String url, Set<?> keys) {
        if (keys == null || keys.isEmpty()) {
            return url;
        }
        String str = "?" + keys.stream().map(k -> String.format("%s={%s}", k, k)).collect(Collectors.joining("&"));
        return url + str;
    }

    public <T> T requestByProxy(String url, Map<String, ?> body, Map<String, ?> uriVariables, HttpHeaders headers, HttpMethod httpMethod, MediaType mediaType, Class<T> responseClazz, String proxyHost, int proxyPort) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }

        RestTemplate restTemplate2 = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress(proxyHost, proxyPort));
        requestFactory.setProxy(proxy);

        restTemplate2.setRequestFactory(requestFactory);

        log.info("request method：{}", httpMethod);
        log.info("request url：{}", url);
        log.info("request header：{}", headers);
        log.info("request uriVariables：{}", uriVariables);
        log.info("request body：{}", JSONUtil.toJsonPrettyStr(body));

        HttpEntity<?> requestEntity = (mediaType == MediaType.APPLICATION_JSON
                || mediaType == MediaType.APPLICATION_JSON_UTF8) ? new HttpEntity<>(new MappingJacksonValue(body), headers)
                : new HttpEntity<>(uriVariables, headers);
        ResponseEntity<T> responseEntity = uriVariables == null ? restTemplate2.exchange(expandURL(url, null), httpMethod, requestEntity, responseClazz)
                : restTemplate2.exchange(expandURL(url, uriVariables.keySet()), httpMethod, requestEntity, responseClazz, uriVariables);
        if (log.isInfoEnabled()) {
            log.info("response ：{}", responseEntity);
        }
        return responseEntity.getBody();
    }

    /**
     * @param url
     * @param keys
     * @return: String
     */
    private String expandURL(String url, Set<?> keys) {
        if (keys == null || keys.isEmpty()) {
            return url;
        }
        String str = "?" + keys.stream().map(k -> String.format("%s={%s}", k, k)).collect(Collectors.joining("&"));
        return url + str;
    }

}
