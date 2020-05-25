package com.moyun.shop.pay.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 签名类型
 * @Author LiuJun
 * @Date 2020/5/22 11:54 上午
 */
@Getter
@AllArgsConstructor
public enum SignType {
    /**
     * MD5
     */
    MD5("MD5");

    private String type;
}
