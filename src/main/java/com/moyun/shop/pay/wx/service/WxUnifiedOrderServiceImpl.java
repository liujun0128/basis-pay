package com.moyun.shop.pay.wx.service;

import com.moyun.shop.pay.domain.enums.SignType;
import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.UnifiedOrderService;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.constants.WxConstants;
import com.moyun.shop.pay.wx.domain.enums.TradeType;
import com.moyun.shop.pay.wx.domain.param.UnifiedOrderParam;
import com.moyun.shop.pay.wx.domain.result.UnifiedOrderResult;
import com.moyun.shop.pay.wx.util.HttpUtil;
import com.moyun.shop.pay.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description 微信下单服务
 * @Author LiuJun
 * @Date 2020/5/20 3:28 下午
 */
@Slf4j
@Service
public class WxUnifiedOrderServiceImpl extends WxBaseServiceImpl implements UnifiedOrderService<UnifiedOrderParam> {

    @Override
    public PayResult order(PayParam<UnifiedOrderParam> param) {
        UnifiedOrderParam reqParam = param.getParam();
        log.info("微信统一下单开始-->请求参数：{}", reqParam.toString());
        String reqXml = getReqXml(reqParam);
        String reqRes = HttpUtil.httpRequest(WxConstants.UNIFIED_ORDER_URL, "POST", reqXml);
        if (PayUtil.isEmpty(reqRes)) {
            log.info("微信统一下单请求异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信统一下单请求异常，请稍后再试。");
        }
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信统一下单失败-->订单号：{}，状态码：{}，错误信息：{}", reqParam.getTradeNo(), result.getCode(), result.getMessage());
            return result;
        }
        log.info("微信统一下单成功-->订单号：{}", reqParam.getTradeNo());
        UnifiedOrderResult data = new UnifiedOrderResult();
        // NATIVE 支付
        if (TradeType.NATIVE.getType().equals(reqParam.getTradeType())) {
            data.setCodeUrl(reqMap.get("code_url"));
        } else {
            data.setTimeStamp(PayUtil.getTimeStamp());
            data.setNonceStr(PayUtil.getRandomString(32));
            data.setPackageStr("prepay_id=" + reqMap.get("prepay_id"));
            data.setSignType(SignType.MD5.getType());
            // 签名Map
            SortedMap<String, Object> resultMap = new TreeMap<>();
            resultMap.put("appId", reqParam.getAppId());
            resultMap.put("timeStamp", data.getTimeStamp());
            resultMap.put("nonceStr", data.getNonceStr());
            resultMap.put("package", data.getPackageStr());
            resultMap.put("signType", data.getSignType());
            String paySign = WxUtil.getSign("UTF-8", resultMap, reqParam.getMchKey());
            data.setPaySign(paySign);
        }
        return new PayResult(data);
    }

    /**
     * 获取请求Xml
     *
     * @param param
     * @return
     */
    private String getReqXml(UnifiedOrderParam param) {
        SortedMap<String, Object> map = new TreeMap<>();
        // 应用id
        map.put("appid", param.getAppId());
        // 商户号
        map.put("mch_id", param.getMchId());
        // 随机字符串
        map.put("nonce_str", PayUtil.getRandomString(32));
        // 商品描述
        map.put("body", param.getBody());
        // 商品详情
        String detail = param.getDetail();
        if (PayUtil.isNotEmpty(detail)) {
            map.put("detail", detail);
        }
        // 附加数据
        String attach = param.getAttach();
        if (PayUtil.isNotEmpty(attach)) {
            map.put("attach", attach);
        }
        // 商户订单号
        map.put("out_trade_no", param.getTradeNo());
        // 标价金额
        map.put("total_fee", param.getTotalFee());
        // 终端IP
        map.put("spbill_create_ip", param.getIp());
        // 交易起始时间
        String startTime = param.getStartTime();
        if (PayUtil.isNotEmpty(startTime)) {
            map.put("time_start", startTime);
        }
        // 交易结束时间
        String expireTime = param.getExpireTime();
        if (PayUtil.isNotEmpty(expireTime)) {
            map.put("time_expire", expireTime);
        }
        // 通知地址
        map.put("notify_url", param.getNotifyUrl());
        // 交易类型
        map.put("trade_type", param.getTradeType());
        // trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
        if (TradeType.NATIVE.getType().equals(param.getTradeType())) {
            map.put("product_id", param.getProductId());
        }
        // 指定支付方式
        String limitPay = param.getLimitPay();
        if (PayUtil.isNotEmpty(limitPay)) {
            map.put("limit_pay", limitPay);
        }
        // trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
        if (TradeType.JSAPI.getType().equals(param.getTradeType())) {
            map.put("openid", param.getOpenId());
        }
        // 获取签名
        String sign = WxUtil.getSign("UTF-8", map, param.getMchKey());
        map.put("sign", sign);
        return WxUtil.getRequestXml(map);
    }
}
