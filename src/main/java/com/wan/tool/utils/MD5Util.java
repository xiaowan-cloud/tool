package com.wan.tool.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wan
 * @version 1.0.0
 * @Description MD5加密
 * @createTime 2021年11月26日 16:14:45
 */
@Slf4j
public class MD5Util {

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String encry(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("加密错误");
            e.printStackTrace();
        }
        byte[] bytes = str.getBytes();
        md.update(bytes);
        System.out.println();
        byte[] digest = md.digest();
        str = parseByte2HexStr(digest);
        str = str.toUpperCase();
        log.info("加密后数据为{}位字符串：{}", str.length(), str);
        return str;
    }

    /**
     * @param buf
     * @return
     * @description 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

}
