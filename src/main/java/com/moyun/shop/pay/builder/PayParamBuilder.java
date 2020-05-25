package com.moyun.shop.pay.builder;

import com.moyun.shop.pay.domain.enums.PayType;
import com.moyun.shop.pay.domain.param.PayParam;

/**
 * @Description 支付参数构造器
 * @Author LiuJun
 * @Date 2020/5/25 9:59 上午
 */
public class PayParamBuilder<T> implements Builder<PayParam> {

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 请求参数
     */
    private T param;

    /**
     * 支付类型设置
     *
     * @param payType
     * @return
     */
    public PayParamBuilder payType(PayType payType) {
        this.payType = payType;
        return this;
    }

    /**
     * 微信统一下单请求参数设置
     *
     * @param param
     * @return
     */
    public PayParamBuilder param(T param) {
        this.param = param;
        return this;
    }

    @Override
    public PayParam build() {
        return new PayParam<T>(payType, param);
    }
}
