package com.moyun.shop.pay.wx.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @Description HttpClient工具类
 * @Author LiuJun
 * @Date 2020/5/24 10:14 上午
 */
@Slf4j
public class HttpClientUtil {

    /**
     * post请求
     *
     * @param sslStockFactory ssl
     * @param requestUrl      请求地址
     * @param reqXml      请求参数
     * @return
     */
    public static String httpPost(SSLConnectionSocketFactory sslStockFactory, String requestUrl, String reqXml) {
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("Host", "api.mch.weixin.qq.com");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Cache-Control", "max-age=0");
        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        httpPost.setEntity(new StringEntity(reqXml, "UTF-8"));
        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslStockFactory).build();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            log.info("微信httpClient请求异常-->请求地址：{}，请求参数：{}，异常：{}", requestUrl, reqXml, e);
            return null;
        }
    }
}
