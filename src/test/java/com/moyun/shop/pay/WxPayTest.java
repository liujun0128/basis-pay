package com.moyun.shop.pay;

import com.moyun.shop.pay.builder.PayParamBuilder;
import com.moyun.shop.pay.domain.enums.PayType;
import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.factory.*;
import com.moyun.shop.pay.service.*;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.domain.enums.TradeType;
import com.moyun.shop.pay.wx.domain.param.*;

import javax.annotation.Resource;

/**
 * @Description 微信支付测试
 * @Author LiuJun
 * @Date 2020/5/25 12:53 下午
 */
public class WxPayTest {

    @Resource
    private UnifiedOrderFactory unifiedOrderFactory;

    @Resource
    private OrderQueryFactory orderQueryFactory;

    @Resource
    private OrderCloseFactory orderCloseFactory;

    @Resource
    private RefundFactory refundFactory;

    @Resource
    private RefundQueryFactory refundQueryFactory;

    /**
     * 统一下单
     */
    public PayResult unifiedOrder() {
        // 微信下单请求参数
        UnifiedOrderParam param = new UnifiedOrderParam();
        param.setBody(PayUtil.getRandomString(24));
        param.setTradeNo(PayUtil.getRandomString(12));
        param.setTotalFee(1);
        param.setNotifyUrl("orderNotifyUrl");
        param.setTradeType(TradeType.JSAPI.getType());
        param.setOpenId("");
        // payType设置支付类型，构建请求参数
        PayParam<UnifiedOrderParam> payParam =
                new PayParamBuilder<UnifiedOrderParam>().payType(PayType.WX_PAY).param(param).build();
        // 获取统一下单实例
        UnifiedOrderService service = unifiedOrderFactory.getService(payParam);
        // 获取执行结果
        PayResult result = service.order(payParam);
        return result;
    }

    /**
     * 订单查询
     */
    public PayResult orderQuery(String orderNo) {
        // 微信订单查询请求参数
        OrderQueryParam param = new OrderQueryParam();
        param.setTradeNo(orderNo);
        // payType设置支付类型，构建请求参数
        PayParam<OrderQueryParam> payParam =
                new PayParamBuilder<OrderQueryParam>().payType(PayType.WX_PAY).param(param).build();
        // 获取订单查询实例
        OrderQueryService service = orderQueryFactory.getService(payParam);
        // 获取执行结果
        PayResult result = service.query(payParam);
        return result;
    }

    /**
     * 订单关闭
     */
    public PayResult orderClose(String orderNo) {
        // 微信订单关闭请求参数
        OrderCloseParam param = new OrderCloseParam();
        param.setTradeNo(orderNo);
        // payType设置支付类型，构建请求参数
        PayParam<OrderCloseParam> payParam =
                new PayParamBuilder<OrderCloseParam>().payType(PayType.WX_PAY).param(param).build();
        // 获取订单关闭实例
        OrderCloseService service = orderCloseFactory.getService(payParam);
        // 获取执行结果
        PayResult result = service.close(payParam);
        return result;
    }

    /**
     * 退款
     */
    public PayResult refund(String orderNo) {
        // 微信退款请求参数
        RefundParam param = new RefundParam();
        param.setTradeNo(orderNo);
        param.setRefundNo(PayUtil.getRandomString(12));
        param.setRefundFee(1);
        param.setTotalFee(1);
        param.setNotifyUrl("refundNotifyUrl");
        // payType设置支付类型，构建请求参数
        PayParam<RefundParam> payParam =
                new PayParamBuilder<RefundParam>().payType(PayType.WX_PAY).param(param).build();
        // 获取退款实例
        RefundService refundService = refundFactory.getService(payParam);
        // 获取执行结果
        PayResult result = refundService.refund(payParam);
        return result;
    }

    /**
     * 退款查询
     */
    public PayResult refundQuery(String orderNo) {
        // 微信退款查询请求参数
        RefundQueryParam param = new RefundQueryParam();
        param.setTradeNo(orderNo);
        // payType设置支付类型，构建请求参数
        PayParam<RefundQueryParam> payParam =
                new PayParamBuilder<RefundQueryParam>().payType(PayType.WX_PAY).param(param).build();
        // 获取退款查询实例
        RefundQueryService service = refundQueryFactory.getService(payParam);
        // 获取执行结果
        PayResult result = service.query(payParam);
        return result;
    }
}
