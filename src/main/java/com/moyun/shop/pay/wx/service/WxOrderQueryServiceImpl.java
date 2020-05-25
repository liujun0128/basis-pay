package com.moyun.shop.pay.wx.service;

import com.moyun.shop.pay.constants.PayConstants;
import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.OrderQueryService;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.constants.WxConstants;
import com.moyun.shop.pay.wx.domain.param.OrderQueryParam;
import com.moyun.shop.pay.wx.domain.result.OrderQueryResult;
import com.moyun.shop.pay.wx.util.HttpUtil;
import com.moyun.shop.pay.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description 微信订单查询服务
 * @Author LiuJun
 * @Date 2020/5/24 4:08 下午
 */
@Slf4j
@Service
public class WxOrderQueryServiceImpl extends WxBaseServiceImpl implements OrderQueryService<OrderQueryParam> {

    @Override
    public PayResult query(PayParam<OrderQueryParam> param) {
        OrderQueryParam reqParam = param.getParam();
        log.info("微信订单查询开始-->请求参数：{}", reqParam.toString());
        String reqXml = getReqXml(reqParam);
        String reqRes = HttpUtil.httpRequest(WxConstants.ORDER_QUERY_URL, "POST", reqXml);
        if (PayUtil.isEmpty(reqRes)) {
            log.info("微信订单查询请求异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信订单查询请求异常，请稍后再试。");
        }
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信订单查询失败-->订单号：{}，状态码：{}，错误信息：{}", reqParam.getTradeNo(), result.getCode(), result.getMessage());
            return result;
        }
        log.info("微信订单查询成功-->订单号：{}", reqParam.getTradeNo());
        OrderQueryResult data = new OrderQueryResult();
        // 商户订单号
        data.setTradeNo(reqMap.get("out_trade_no"));
        // 附加数据
        data.setAttach(reqMap.get("attach"));
        // 交易状态描述
        data.setTradeStateDesc(reqMap.get("trade_state_desc"));
        // 交易状态
        String tradeState = reqMap.get("trade_state");
        if (PayConstants.SUCCESS.equals(tradeState)) {
            // 交易类型
            data.setTradeType(reqMap.get("trade_type"));
            // 付款银行
            data.setBankType(reqMap.get("bank_type"));
            // 订单金额
            data.setTotalFee(Integer.valueOf(reqMap.get("total_fee")));
            // 现金支付金额
            data.setCashFee(Integer.valueOf(reqMap.get("cash_fee")));
            // 微信支付订单号
            data.setTransactionId(reqMap.get("transaction_id"));
            // 支付完成时间
            data.setEndTime(reqMap.get("time_end"));
        }
        return new PayResult(data);
    }

    /**
     * 获取请求Xml
     *
     * @param param
     * @return
     */
    private String getReqXml(OrderQueryParam param) {
        SortedMap<String, Object> map = new TreeMap<>();
        // 应用id
        map.put("appid", param.getAppId());
        // 商户号
        map.put("mch_id", param.getMchId());
        // 随机字符串
        map.put("nonce_str", PayUtil.getRandomString(32));
        // 微信订单号
        String transactionId = param.getTransactionId();
        if (PayUtil.isNotEmpty(transactionId)) {
            map.put("transaction_id", transactionId);
        }
        // 商户订单号
        String tradeNo = param.getTradeNo();
        if (PayUtil.isNotEmpty(tradeNo)) {
            map.put("out_trade_no", tradeNo);
        }
        // 获取签名
        String sign = WxUtil.getSign("UTF-8", map, param.getMchKey());
        map.put("sign", sign);
        return WxUtil.getRequestXml(map);
    }
}
