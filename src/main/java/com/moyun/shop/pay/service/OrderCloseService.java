package com.moyun.shop.pay.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;

/**
 * @Description 订单关闭服务
 * @Author LiuJun
 * @Date 2020/5/24 5:11 下午
 */
public interface OrderCloseService<T> extends BaseService {

    /**
     * 关闭
     *
     * @param param
     * @return
     */
    PayResult close(PayParam<T> param);
}
