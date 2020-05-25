package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

import java.util.List;

/**
 * @Description 退款查询结果
 * @Author LiuJun
 * @Date 2020/5/24 5:40 下午
 */
@Data
public class RefundQueryResult {
    /**
     * 交易状态描述
     */
    private String tradeStateDesc;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 订单金额
     */
    private Integer totalFee;

    /**
     * 退款金额
     */
    private Integer refundFee;

    /**
     * 现金支付金额
     */
    private Integer cashFee;

    /**
     * 退款笔数
     */
    private Integer refundCount;

    /**
     * 退款明细
     */
    private List<RefundDetail> details;
}
