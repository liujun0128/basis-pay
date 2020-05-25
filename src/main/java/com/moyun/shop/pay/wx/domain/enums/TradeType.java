package com.moyun.shop.pay.wx.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 交易类型
 * @Author LiuJun
 * @Date 2020/5/20 3:04 下午
 */
@Getter
@AllArgsConstructor
public enum TradeType {
    /**
     * JSAPI支付（或小程序支付）
     */
    JSAPI("JSAPI"),
    /**
     * Native支付
     */
    NATIVE("NATIVE"),
    /**
     * app支付
     */
    APP("APP"),
    /**
     * H5支付
     */
    MWEB("MWEB");

    private String type;
}
