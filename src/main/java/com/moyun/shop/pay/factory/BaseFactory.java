package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;

/**
 * @Description 抽象工厂
 * @Author LiuJun
 * @Date 2020/5/20 3:31 下午
 */
public abstract class BaseFactory<T> {

    /**
     * 获取服务实例
     *
     * @param param
     * @return
     */
    public abstract T getService(PayParam param);
}
