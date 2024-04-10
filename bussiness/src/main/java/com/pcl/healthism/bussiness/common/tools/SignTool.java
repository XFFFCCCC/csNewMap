package com.pcl.healthism.bussiness.common.tools;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class SignTool {

    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        }
    }

    public static String sign(String content) {
        if (StringTool.isEmpty(content)) {
            return "";
        }
        return new String(messageDigest.digest(content.getBytes()));
    }

}
