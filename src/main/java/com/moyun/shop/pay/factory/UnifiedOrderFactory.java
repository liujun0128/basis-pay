package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.service.UnifiedOrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 下单工厂类
 * @Author LiuJun
 * @Date 2020/5/24 4:06 下午
 */
@Component
public class UnifiedOrderFactory extends BaseFactory<UnifiedOrderService> {

    @Resource
    private List<UnifiedOrderService> unifiedOrderServices;

    @Override
    public UnifiedOrderService getService(PayParam param) {
        for (UnifiedOrderService service : unifiedOrderServices) {
            if (service.support(param.getPayType())) {
                return service;
            }
        }
        throw new UnsupportedOperationException("OrderService unsupported payType: " + param.getPayType().getType());
    }
}
