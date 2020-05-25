package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.service.OrderQueryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 订单查询工厂类
 * @Author LiuJun
 * @Date 2020/5/24 4:06 下午
 */
@Component
public class OrderQueryFactory extends BaseFactory<OrderQueryService> {

    @Resource
    private List<OrderQueryService> orderQueryServices;

    @Override
    public OrderQueryService getService(PayParam param) {
        for (OrderQueryService service : orderQueryServices) {
            if (service.support(param.getPayType())) {
                return service;
            }
        }
        throw new UnsupportedOperationException("OrderQueryService unsupported payType: " + param.getPayType().getType());
    }
}
