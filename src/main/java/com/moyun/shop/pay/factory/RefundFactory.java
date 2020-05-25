package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.service.RefundService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 退款工厂类
 * @Author LiuJun
 * @Date 2020/5/22 6:17 下午
 */
@Component
public class RefundFactory extends BaseFactory<RefundService> {

    @Resource
    private List<RefundService> refundServices;

    @Override
    public RefundService getService(PayParam param) {
        for (RefundService service : refundServices) {
            if (service.support(param.getPayType())) {
                return service;
            }
        }
        throw new UnsupportedOperationException("RefundService unsupported payType: " + param.getPayType().getType());
    }
}
