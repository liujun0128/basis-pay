package com.moyun.shop.pay.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 支付类型
 * @Author LiuJun
 * @Date 2020/5/19 3:44 下午
 */
@Getter
@AllArgsConstructor
public enum PayType {
    /**
     * 微信支付
     */
    WX_PAY(1),
    /**
     * 支付宝支付
     */
    ALI_PAY(2),
    /**
     * 银联支付（个人）
     */
    UNION_PAY(3),
    /**
     * 银联支付（企业）
     */
    CHINA_PAY(4);

    private Integer type;
}
