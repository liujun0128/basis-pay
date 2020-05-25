package com.moyun.shop.pay.wx.service;

import com.moyun.shop.pay.constants.PayConstants;
import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.RefundQueryService;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.constants.WxConstants;
import com.moyun.shop.pay.wx.domain.param.RefundQueryParam;
import com.moyun.shop.pay.wx.domain.result.RefundDetail;
import com.moyun.shop.pay.wx.domain.result.RefundQueryResult;
import com.moyun.shop.pay.wx.util.HttpUtil;
import com.moyun.shop.pay.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 微信退款查询服务
 * @Author LiuJun
 * @Date 2020/5/24 5:40 下午
 */
@Slf4j
@Service
public class WxRefundQueryServiceImpl extends WxBaseServiceImpl implements RefundQueryService<RefundQueryParam> {

    @Override
    public PayResult query(PayParam<RefundQueryParam> param) {
        RefundQueryParam reqParam = param.getParam();
        log.info("微信退款查询开始-->请求参数：{}", reqParam.toString());
        String reqXml = getReqXml(reqParam);
        String reqRes = HttpUtil.httpRequest(WxConstants.REFUND_QUERY_URL, "POST", reqXml);
        if (PayUtil.isEmpty(reqRes)) {
            log.info("微信退款查询请求异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信退款查询请求异常，请稍后再试。");
        }
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信退款查询失败-->订单号：{}，状态码：{}，错误信息：{}", reqParam.getTradeNo(), result.getCode(), result.getMessage());
            return result;
        }
        log.info("微信退款查询成功-->订单号：{}", reqParam.getTradeNo());
        RefundQueryResult data = new RefundQueryResult();
        // 微信订单号
        data.setTransactionId(reqMap.get("transaction_id"));
        // 商户订单号
        data.setTradeNo(reqMap.get("out_trade_no"));
        // 交易状态
        String tradeState = reqMap.get("trade_state");
        if (PayUtil.isNotEmpty(tradeState)) {
            // 交易状态描述
            data.setTradeStateDesc(reqMap.get("trade_state_desc"));
        } else {
            // 订单金额
            data.setTotalFee(Integer.valueOf(reqMap.get("total_fee")));
            // 现金支付金额
            data.setCashFee(Integer.valueOf(reqMap.get("cash_fee")));
            // 退款金额
            data.setRefundFee(Integer.valueOf(reqMap.get("refund_fee")));
            // 退款笔数
            int refundCount = Integer.valueOf(reqMap.get("refund_count"));
            data.setRefundCount(refundCount);
            // 退款明细
            List<RefundDetail> details = new ArrayList<>();
            for (int i = 0; i < refundCount; i++) {
                final int idx = i;
                details.add(new RefundDetail() {{
                    // 商户退款单号
                    setRefundNo(reqMap.get("out_refund_no_" + idx));
                    // 微信退款单号
                    setRefundId(reqMap.get("refund_id_" + idx));
                    // 退款渠道
                    setRefundChannel(reqMap.get("refund_channel_" + idx));
                    // 申请退款金额
                    setRefundFee(Integer.valueOf(reqMap.get("refund_fee_" + idx)));
                    // 退款状态
                    setRefundStatus(reqMap.get("refund_status_" + idx));
                    // 退款资金来源
                    setRefundAccount(reqMap.get("refund_account_" + idx));
                    // 退款入账账户
                    setRefundRecvAccout(reqMap.get("refund_recv_accout_" + idx));
                    // 退款成功时间
                    if (PayConstants.SUCCESS.equals(reqMap.get("refund_status_" + idx))) {
                        setRefundSuccessTime(reqMap.get("refund_success_time_" + idx));
                    }
                }});
            }
            data.setDetails(details);
        }
        return new PayResult(data);
    }

    /**
     * 获取请求Xml
     *
     * @param param
     * @return
     */
    private String getReqXml(RefundQueryParam param) {
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
        // 微信退款单号
        String refundId = param.getRefundId();
        if (PayUtil.isNotEmpty(refundId)) {
            map.put("refund_id", refundId);
        }
        // 商户退款单号
        String refundNo = param.getRefundNo();
        if (PayUtil.isNotEmpty(refundNo)) {
            map.put("out_refund_no", refundNo);
        }
        // 获取签名
        String sign = WxUtil.getSign("UTF-8", map, param.getMchKey());
        map.put("sign", sign);
        return WxUtil.getRequestXml(map);
    }
}
