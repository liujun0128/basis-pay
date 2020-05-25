package com.moyun.shop.pay.builder;

import java.io.Serializable;

/**
 * @Description 建造者模式接口定义
 * @Author LiuJun
 * @Date 2020/5/19 3:48 下午
 */
public interface Builder<T> extends Serializable {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();
}
