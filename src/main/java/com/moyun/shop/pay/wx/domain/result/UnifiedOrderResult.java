package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 统一下单结果
 * @Author LiuJun
 * @Date 2020/5/22 9:25 上午
 */
@Data
public class UnifiedOrderResult {

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机串
     */
    private String nonceStr;

    /**
     * 数据包
     */
    private String packageStr;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 签名
     */
    private String paySign;

    /**
     * 二维码链接
     */
    private String codeUrl;
}
