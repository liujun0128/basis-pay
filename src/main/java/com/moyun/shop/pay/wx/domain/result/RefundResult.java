package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 退款结果
 * @Author LiuJun
 * @Date 2020/5/22 9:25 上午
 */
@Data
public class RefundResult {

    /**
     * 微信退款单号
     */
    private String refundId;

    /**
     * 退款金额
     */
    private String refundFee;
}
