package com.wan.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wan
 * @version 1.0.0
 * @Description AES加密128位CBC模式工具类
 * @createTime 2021年12月14日 11:12:20
 */
@Component
@Slf4j
public class AES128Util {

    /**
     * 密钥key
     */
    public static final String KEY = "755c3149492ea59070766b817f981e5e";
    /**
     * 向量iv
     */
    public static final String IV = "!WFNZFU_{H%M(S|a";

    /**
     * 功能描述：
     * -- 加密
     *
     * @param : [content]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/12/14 11:15
     */
    public static String encrypt(String content) {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception ex) {
            log.error("加密错误=====>:{}", ex.getMessage());
            return null;
        }
    }

    /**
     * 功能描述：
     * -- 解密
     *
     * @param : [content]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/12/14 11:15
     */
    public static String decrypt(String content) {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception ex) {
            log.error("解密错误=====>:{}", ex.getMessage());
            return null;
        }
    }

}
