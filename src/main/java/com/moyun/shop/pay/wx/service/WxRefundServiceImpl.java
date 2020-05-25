package com.moyun.shop.pay.wx.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.RefundService;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.constants.WxConstants;
import com.moyun.shop.pay.wx.domain.param.RefundParam;
import com.moyun.shop.pay.wx.domain.result.RefundResult;
import com.moyun.shop.pay.wx.ssl.SslSocketFactoryBuilder;
import com.moyun.shop.pay.wx.util.HttpClientUtil;
import com.moyun.shop.pay.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description 微信退款服务
 * @Author LiuJun
 * @Date 2020/5/22 6:22 下午
 */
@Slf4j
@Service
public class WxRefundServiceImpl extends WxBaseServiceImpl implements RefundService<RefundParam> {

    @Override
    public PayResult refund(PayParam<RefundParam> param) {
        RefundParam reqParam = param.getParam();
        log.info("微信退款开始-->请求参数：{}", reqParam.toString());
        String reqXml = getReqXml(reqParam);
        // 创建ssl类型的client
        SSLConnectionSocketFactory sslStockFactory =
                SslSocketFactoryBuilder.create().setCertPath(reqParam.getCertPath()).setPwd(reqParam.getMchId()).build();
        if (sslStockFactory == null) {
            log.info("微信退款构建SSLConnectionSocketFactory异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信退款请求异常，请稍后再试。");
        }

        // 退款请求
        String reqRes = HttpClientUtil.httpPost(sslStockFactory, WxConstants.REFUND_URL, reqXml);
        if (PayUtil.isEmpty(reqRes)) {
            log.info("微信退款请求异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信退款请求异常，请稍后再试。");
        }
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信退款失败-->订单号：{}，状态码：{}，错误信息：{}", reqParam.getTradeNo(), result.getCode(), result.getMessage());
            return result;
        }
        log.info("微信退款成功-->订单号：{}", reqParam.getTradeNo());
        RefundResult data = new RefundResult();
        data.setRefundId(reqMap.get("refund_id"));
        data.setRefundFee(reqMap.get("refund_fee"));
        return new PayResult(data);
    }

    /**
     * 获取请求xml
     *
     * @param param
     * @return
     */
    private String getReqXml(RefundParam param) {
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
        // 商户退款单号
        map.put("out_refund_no", param.getRefundNo());
        // 订单金额
        map.put("total_fee", param.getTotalFee());
        // 退款金额
        map.put("refund_fee", param.getRefundFee());
        // 退款原因
        String refundDesc = param.getRefundDesc();
        if (PayUtil.isNotEmpty(refundDesc)) {
            map.put("refund_desc", refundDesc);
        }
        // 退款资金来源
        String refundAccount = param.getRefundAccount();
        if (PayUtil.isNotEmpty(refundAccount)) {
            map.put("refund_account", refundAccount);
        }
        // 退款结果通知url
        String notifyUrl = param.getNotifyUrl();
        if (PayUtil.isNotEmpty(notifyUrl)) {
            map.put("notify_url", notifyUrl);
        }
        // 获取签名
        String sign = WxUtil.getSign("UTF-8", map, param.getMchKey());
        map.put("sign", sign);
        return WxUtil.getRequestXml(map);
    }
}
