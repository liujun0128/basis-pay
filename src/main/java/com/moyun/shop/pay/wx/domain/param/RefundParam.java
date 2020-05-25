package com.moyun.shop.pay.wx.domain.param;

import lombok.Data;

/**
 * @Description 退款请求参数
 * @Author LiuJun
 * @Date 2020/5/24 5:33 下午
 */
@Data
public class RefundParam extends BaseParam {

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
     * 订单金额
     */
    private Integer totalFee;

    /**
     * 退款金额
     */
    private Integer refundFee;

    /**
     * 退款原因
     */
    private String refundDesc;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    /**
     * 通知地址
     */
    private String notifyUrl;
}
