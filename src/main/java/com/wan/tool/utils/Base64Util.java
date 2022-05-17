package com.wan.tool.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

/**
 * @author wan
 * @version 1.0.0
 * @Description Base64加密
 * @createTime 2021年11月26日 16:25:14
 */
@Slf4j
public class Base64Util {

    /**
     * 功能描述：
     * -- base64加密
     *
     * @param : [str]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/11/26 16:26
     */
    public static String encry(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(str.getBytes());
        return encode.toString();
    }

    /**
     * 功能描述：
     * -- base64解密
     *
     * @param : [str]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/11/26 16:28
     */
    public static String decrypt(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(str.getBytes());
        return decode.toString();
    }

}
