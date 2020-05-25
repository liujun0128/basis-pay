package com.moyun.shop.pay.wx.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description Http工具类
 * @Author LiuJun
 * @Date 2020/5/24 12:10 下午
 */
@Slf4j
public class HttpUtil {

    /**
     * 请求微信接口
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式
     * @param reqXml    请求xml
     * @return
     */
    public static String httpRequest(String requestUrl, String requestMethod, String reqXml) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpUrlConnection(requestUrl, requestMethod);
        } catch (Exception e) {
            if (conn != null) {
                conn.disconnect();
            }
            log.info("微信http请求异常-->请求地址：{}，请求参数：{}，异常：{}", requestUrl, reqXml, e);
            return null;
        }
        try (OutputStream outputStream = conn.getOutputStream()) {
            outputStream.write(reqXml.getBytes("UTF-8"));
        } catch (IOException e) {
            if (conn != null) {
                conn.disconnect();
            }
            log.info("微信http请求异常-->请求地址：{}，请求参数：{}，异常：{}", requestUrl, reqXml, e);
            return null;
        }
        try (InputStream inputStream = conn.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            if (conn != null) {
                conn.disconnect();
            }
            return buffer.toString();
        } catch (IOException e) {
            if (conn != null) {
                conn.disconnect();
            }
            log.info("微信http请求异常-->请求地址：{}，请求参数：{}，异常：{}", requestUrl, reqXml, e);
            return null;
        }
    }

    /**
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式
     * @return
     */
    private static HttpURLConnection getHttpUrlConnection(String requestUrl, String requestMethod) throws Exception {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        // 设置请求方式（GET/POST）
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        return conn;
    }
}
