package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.enums.PayType;

/**
 * @Description 公用服务
 * @Author LiuJun
 * @Date 2020/5/19 5:30 下午
 */
public interface BaseService {

    /**
     * 判断支付类型
     *
     * @param payType
     * @return
     */
    Boolean support(PayType payType);
}
