package com.moyun.shop.pay.wx.util;

import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.domain.result.OrderNotifyResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description 下单通知工具类
 * @Author LiuJun
 * @Date 2020/5/24 2:38 下午
 */
@Slf4j
public class OrderNotifyUtil {

    /**
     * 获取通知结果
     *
     * @param request
     * @return
     */
    public static PayResult getNotify(HttpServletRequest request) {
        String reqRes = WxUtil.getNotifyXml(request);
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信下单通知失败-->通知内容：{}，状态码：{}，错误信息：{}", reqRes, result.getCode(),
                    result.getMessage());
            return result;
        }
        OrderNotifyResult data = new OrderNotifyResult();
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
        // 商户订单号
        data.setTradeNo(reqMap.get("out_trade_no"));
        // 商家数据包
        data.setAttach(reqMap.get("attach"));
        // 支付完成时间
        data.setEndTime(reqMap.get("time_end"));
        return new PayResult(data);
    }
}
