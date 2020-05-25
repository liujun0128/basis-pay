package com.moyun.shop.pay.wx.domain.result;

import lombok.Data;

/**
 * @Description 订单查询结果
 * @Author LiuJun
 * @Date 2020/5/24 4:03 下午
 */
@Data
public class OrderQueryResult extends OrderNotifyResult {

    /**
     * 交易状态描述
     */
    private String tradeStateDesc;
}
