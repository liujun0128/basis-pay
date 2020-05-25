package com.moyun.shop.pay.util;

import java.util.Random;

/**
 * @Description 通用工具类
 * @Author LiuJun
 * @Date 2020/5/22 3:55 下午
 */
public class PayUtil {

    /**
     * 随机字符串
     */
    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 随机字符串生成
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(RANDOM_STR.length());
            sb.append(RANDOM_STR.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 判断非空
     *
     * @param str
     * @return
     */
    public static Boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 判断空
     *
     * @param str
     * @return
     */
    public static Boolean isEmpty(Object str) {
        if (str == null) {
            return true;
        }
        return str.toString().length() == 0;
    }
}
