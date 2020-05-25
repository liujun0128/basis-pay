package com.moyun.shop.pay.wx.domain.param;

import lombok.Data;

/**
 * @Description 退款查询请求参数
 * @Author LiuJun
 * @Date 2020/5/24 5:33 下午
 */
@Data
public class RefundQueryParam extends BaseParam {

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户退款单号
     */
    private String refundNo;

    /**
     * 微信退款单号
     */
    private String refundId;
}
