package com.moyun.shop.pay.ali.service;

import com.moyun.shop.pay.domain.enums.PayType;
import com.moyun.shop.pay.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 支付宝公用服务
 * @Author LiuJun
 * @Date 2020/5/20 4:52 下午
 */
@Slf4j
@Service
public class AliBaseServiceImpl implements BaseService {

    @Override
    public Boolean support(PayType payType) {
        return PayType.ALI_PAY.equals(payType);
    }
}
