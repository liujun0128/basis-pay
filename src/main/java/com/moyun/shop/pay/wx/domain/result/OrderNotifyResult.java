package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 统一下单通知结果
 * @Author LiuJun
 * @Date 2020/5/24 2:18 下午
 */
@Data
public class OrderNotifyResult {

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 付款银行
     */
    private String bankType;

    /**
     * 订单金额
     */
    private Integer totalFee;

    /**
     * 现金支付金额
     */
    private Integer cashFee;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 商家数据包
     */
    private String attach;

    /**
     * 支付完成时间，格式为yyyyMMddHHmmss
     */
    private String endTime;
}
