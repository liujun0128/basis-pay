package com.moyun.shop.pay.util;

import com.moyun.shop.pay.domain.enums.SignType;

import java.security.MessageDigest;

/**
 * @Description MD5工具类
 * @Author LiuJun
 * @Date 2020/5/20 11:25 下午
 */
public class Md5Util {
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            res.append(byteToHexString(b[i]));
        }
        return res.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String md5Encode(String origin, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance(SignType.MD5.getType());
            if (charsetName == null || "".equals(charsetName)) {
                origin = byteArrayToHexString(md.digest(origin
                        .getBytes()));
            } else {
                origin = byteArrayToHexString(md.digest(origin
                        .getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        return origin;
    }
}
