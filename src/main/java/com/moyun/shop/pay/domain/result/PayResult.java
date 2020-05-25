package com.moyun.shop.pay.domain.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 通用支付返回
 * @Author LiuJun
 * @Date 2020/5/7 11:48 上午
 */
@Data
public class PayResult<T> implements Serializable {

    /**
     * 0000为返回正常， 其它code均为请求错误
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 默认返回
     */
    public PayResult() {
        this.code = "0000";
        this.message = "";
    }

    /**
     * 返回数据
     *
     * @param data
     */
    public PayResult(T data) {
        this();
        this.data = data;
    }

    /**
     * 返回错误信息
     *
     * @param message
     */
    public PayResult(String message) {
        this.code = "9999";
        this.message = message;
    }

    /**
     * 返回自定义code和错误信息
     *
     * @param code
     * @param message
     */
    public PayResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
