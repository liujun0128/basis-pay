package com.moyun.shop.pay.wx.ssl;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * @Description SSLSocketFactory构建器
 * @Author LiuJun
 * @Date 2020/5/24 8:48 上午
 */
@Slf4j
public class SslSocketFactoryBuilder {

    /**
     * TLSv1协议
     */
    public static final String[] PROTOCOL = {"TLSv1"};

    /**
     * 秘钥路径
     */
    private String certPath;
    /**
     * 秘钥密码
     */
    private String pwd;


    /**
     * 创建 SSLSocketFactoryBuilder
     *
     * @return SSLSocketFactoryBuilder
     */
    public static SslSocketFactoryBuilder create() {
        return new SslSocketFactoryBuilder();
    }

    /**
     * 设置秘钥路径
     *
     * @return 自身
     */
    public SslSocketFactoryBuilder setCertPath(String certPath) {
        this.certPath = certPath;
        return this;
    }

    /**
     * 设置密码
     *
     * @return 自身
     */
    public SslSocketFactoryBuilder setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    /**
     * 构建SSLConnectionSocketFactory
     *
     * @return SSLConnectionSocketFactory
     */
    public SSLConnectionSocketFactory build() {
        try (FileInputStream fis = new FileInputStream(new File(certPath))) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // 指定PKCS12的密码
            char[] pwdChar = pwd.toCharArray();
            keyStore.load(fis, pwdChar);
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, pwdChar).build();
            // 指定TLS版本
            return new SSLConnectionSocketFactory(sslcontext, PROTOCOL,
                    null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (Exception e) {
            log.info("微信退款构建SSLConnectionSocketFactory异常-->{}", e);
            return null;
        }
    }
}
