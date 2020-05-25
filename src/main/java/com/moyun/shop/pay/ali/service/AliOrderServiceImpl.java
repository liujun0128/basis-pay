package com.moyun.shop.pay.ali.service;

import com.moyun.shop.pay.ali.domain.param.AliOrderParam;
import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.UnifiedOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 支付宝下单服务
 * @Author LiuJun
 * @Date 2020/5/20 6:13 下午
 */
@Slf4j
@Service
public class AliOrderServiceImpl extends AliBaseServiceImpl implements UnifiedOrderService<AliOrderParam> {

    @Override
    public PayResult order(PayParam<AliOrderParam> param) {
        return null;
    }
}
