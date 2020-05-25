package com.moyun.shop.pay.factory;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.service.RefundQueryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 退款查询工厂类
 * @Author LiuJun
 * @Date 2020/5/24 5:37 下午
 */
@Component
public class RefundQueryFactory extends BaseFactory<RefundQueryService> {

    @Resource
    private List<RefundQueryService> refundServices;

    @Override
    public RefundQueryService getService(PayParam param) {
        for (RefundQueryService service : refundServices) {
            if (service.support(param.getPayType())) {
                return service;
            }
        }
        throw new UnsupportedOperationException("RefundQueryService unsupported payType: " + param.getPayType().getType());
    }
}
