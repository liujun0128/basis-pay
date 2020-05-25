package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;

/**
 * @Description 退款查询服务
 * @Author LiuJun
 * @Date 2020/5/24 5:38 下午
 */
public interface RefundQueryService<T> extends BaseService {

    /**
     * 退款
     *
     * @param param
     * @return
     */
    PayResult query(PayParam<T> param);
}
