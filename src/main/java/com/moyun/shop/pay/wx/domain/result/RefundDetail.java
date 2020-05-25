package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 退款明细
 * @Author LiuJun
 * @Date 2020/5/24 5:40 下午
 */
@Data
public class RefundDetail {
    /**
     * 商户退款单号
     */
    private String refundNo;

    /**
     * 微信退款单号
     */
    private String refundId;

    /**
     * 退款渠道
     */
    private String refundChannel;

    /**
     * 申请退款金额
     */
    private Integer refundFee;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    /**
     * 退款入账账户
     */
    private String refundRecvAccout;

    /**
     * 退款成功时间
     */
    private String refundSuccessTime;
}
