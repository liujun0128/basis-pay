package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 退款通知结果
 * @Author LiuJun
 * @Date 2020/5/24 3:16 下午
 */
@Data
public class RefundNotifyResult {

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 微信退款单号
     */
    private String refundId;

    /**
     * 商户退款单号
     */
    private String refundNo;

    /**
     * 订单金额
     */
    private Integer totalFee;

    /**
     * 申请退款金额
     */
    private Integer refundFee;

    /**
     * 退款金额
     */
    private Integer settlementRefundFee;

    /**
     * 退款成功时间，格式为yyyyMMddHHmmss
     */
    private String successTime;

    /**
     * 退款入账账户
     */
    private String refundRecvAccount;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    /**
     * 退款发起来源
     */
    private String refundRequestSource;
}
