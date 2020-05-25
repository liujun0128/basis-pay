package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;

/**
 * @Description 退款服务
 * @Author LiuJun
 * @Date 2020/5/22 6:16 下午
 */
public interface RefundService<T> extends BaseService {

    /**
     * 退款
     *
     * @param param
     * @return
     */
    PayResult refund(PayParam<T> param);
}
