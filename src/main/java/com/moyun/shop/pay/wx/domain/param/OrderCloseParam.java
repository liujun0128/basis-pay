package com.moyun.shop.pay.wx.domain.param;

import lombok.Data;

/**
 * @Description 订单关闭请求参数
 * @Author LiuJun
 * @Date 2020/5/24 5:09 下午
 */
@Data
public class OrderCloseParam extends BaseParam {

    /**
     * 商户订单号
     */
    private String tradeNo;
}
