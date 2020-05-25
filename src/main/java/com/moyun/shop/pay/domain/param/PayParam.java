package com.moyun.shop.pay.domain.param;

import com.moyun.shop.pay.domain.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 支付参数
 * @Author LiuJun
 * @Date 2020/5/25 9:51 上午
 */
@Data
@AllArgsConstructor
public class PayParam<T> implements Serializable {

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 请求参数
     */
    private T param;
}
