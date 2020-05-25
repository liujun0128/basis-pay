package com.moyun.shop.pay.wx.properties;

import com.moyun.shop.pay.util.PropertiesUtil;
import lombok.Getter;

import java.io.Serializable;
import java.util.Properties;

/**
 * @Description 微信支付配置文件
 * @Author LiuJun
 * @Date 2020/5/25 11:06 上午
 */
@Getter
public class WxPayProperties implements Serializable {

    /**
     * 应用Id
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 秘钥地址
     */
    private String certPath;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 终端IP
     */
    private String ip;

    /**
     * 必须声明 volatile，在多线程中instance被修改后能立即更新到主内存中
     * 如果不声明，就不是线程安全的
     */
    private volatile static WxPayProperties WX_PAY = null;

    /**
     * 构造方法私有，不允许通过New创建对象
     */
    private WxPayProperties() {
        if (WX_PAY != null) {
            throw new RuntimeException("singleton constructor is called... ");
        }
    }

    /**
     * 构造方法私有，不允许通过New创建对象
     */
    private WxPayProperties(String appId, String mchId, String certPath, String mchKey, String ip) {
        this.appId = appId;
        this.mchId = mchId;
        this.certPath = certPath;
        this.mchKey = mchKey;
        this.ip = ip;
    }

    /**
     * 通过反序列化创建对象，直接返回
     */
    private Object readResolve() {
        return getInstance();
    }

    /**
     * 通过该方法获取实例对象
     *
     * @return
     */
    public static WxPayProperties getInstance() {
        if (WX_PAY == null) {
            synchronized (WxPayProperties.class) {
                if (WX_PAY == null) {
                    Properties properties = PropertiesUtil.getProp("wx-pay.properties");
                    WX_PAY = new WxPayProperties(properties.getProperty("wx.pay.appId"),
                            properties.getProperty("wx.pay.mchId"),
                            properties.getProperty("wx.pay.certPath"),
                            properties.getProperty("wx.pay.mchKey"),
                            properties.getProperty("wx.pay.ip"));
                }
            }
        }
        return WX_PAY;
    }
}
