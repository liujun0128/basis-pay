package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;

/**
 * @Description 统一下单服务
 * @Author LiuJun
 * @Date 2020/5/20 3:27 下午
 */
public interface UnifiedOrderService<T> extends BaseService {

    /**
     * 下单
     *
     * @param param
     * @return
     */
    PayResult order(PayParam<T> param);
}
