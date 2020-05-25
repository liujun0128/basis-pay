package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.service.OrderCloseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 订单关闭工厂类
 * @Author LiuJun
 * @Date 2020/5/24 5:16 下午
 */
@Component
public class OrderCloseFactory extends BaseFactory<OrderCloseService> {

    @Resource
    private List<OrderCloseService> orderCloseServices;

    @Override
    public OrderCloseService getService(PayParam param) {
        for (OrderCloseService service : orderCloseServices) {
            if (service.support(param.getPayType())) {
                return service;
            }
        }
        throw new UnsupportedOperationException("OrderCloseService unsupported payType: " + param.getPayType().getType());
    }
}
