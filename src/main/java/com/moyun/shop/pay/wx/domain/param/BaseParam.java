package com.moyun.shop.pay.wx.domain.param;

import com.moyun.shop.pay.wx.properties.WxPayProperties;
import lombok.Data;

/**
 * @Description 微信下单请求参数
 * @Author LiuJun
 * @Date 2020/5/19 4:34 下午
 */
@Data
public class BaseParam {

    /**
     * 无参构造
     */
    public BaseParam() {
        WxPayProperties properties = WxPayProperties.getInstance();
        this.appId = properties.getAppId();
        this.mchId = properties.getMchId();
        this.certPath = properties.getCertPath();
        this.mchKey = properties.getMchKey();
        this.ip = properties.getIp();
    }

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
}
