package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;

/**
 * @Description 订单查询服务
 * @Author LiuJun
 * @Date 2020/5/24 3:58 下午
 */
public interface OrderQueryService<T> extends BaseService {

    /**
     * 查询
     *
     * @param param
     * @return
     */
    PayResult query(PayParam<T> param);
}
