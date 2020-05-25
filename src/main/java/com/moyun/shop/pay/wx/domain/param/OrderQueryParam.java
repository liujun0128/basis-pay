package com.moyun.shop.pay.wx.domain.param;

import lombok.Data;

/**
 * @Description 订单查询请求参数
 * @Author LiuJun
 * @Date 2020/5/24 4:00 下午
 */
@Data
public class OrderQueryParam extends BaseParam {

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 微信订单号
     */
    private String transactionId;
}
