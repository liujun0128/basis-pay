package com.moyun.shop.pay.wx.service;

import com.moyun.shop.pay.domain.param.PayParam;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.service.OrderCloseService;
import com.moyun.shop.pay.util.PayUtil;
import com.moyun.shop.pay.wx.constants.WxConstants;
import com.moyun.shop.pay.wx.domain.param.OrderCloseParam;
import com.moyun.shop.pay.wx.domain.result.OrderCloseResult;
import com.moyun.shop.pay.wx.util.HttpUtil;
import com.moyun.shop.pay.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description 微信订单关闭服务
 * @Author LiuJun
 * @Date 2020/5/24 5:21 下午
 */
@Slf4j
@Service
public class WxOrderCloseServiceImpl extends WxBaseServiceImpl implements OrderCloseService<OrderCloseParam> {

    @Override
    public PayResult close(PayParam<OrderCloseParam> param) {
        OrderCloseParam reqParam = param.getParam();
        log.info("微信订单关闭开始-->请求参数：{}", reqParam.toString());
        String reqXml = getReqXml(reqParam);
        String reqRes = HttpUtil.httpRequest(WxConstants.ORDER_CLOSE_URL, "POST", reqXml);
        if (PayUtil.isEmpty(reqRes)) {
            log.info("微信订单关闭请求异常-->订单号：{}", reqParam.getTradeNo());
            return new PayResult("微信订单关闭请求异常，请稍后再试。");
        }
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        PayResult result = WxUtil.getMessage(reqMap);
        if (PayUtil.isNotEmpty(result.getMessage())) {
            log.info("微信订单关闭失败-->订单号：{}，状态码：{}，错误信息：{}", reqParam.getTradeNo(), result.getCode(), result.getMessage());
            return result;
        }
        log.info("微信订单关闭成功-->订单号：{}", reqParam.getTradeNo());
        OrderCloseResult data = new OrderCloseResult();
        return new PayResult(data);
    }

    /**
     * 获取请求Xml
     *
     * @param param
     * @return
     */
    private String getReqXml(OrderCloseParam param) {
        SortedMap<String, Object> map = new TreeMap<>();
        // 应用id
        map.put("appid", param.getAppId());
        // 商户号
        map.put("mch_id", param.getMchId());
        // 随机字符串
        map.put("nonce_str", PayUtil.getRandomString(32));
        // 商户订单号
        map.put("out_trade_no", param.getTradeNo());
        // 获取签名
        String sign = WxUtil.getSign("UTF-8", map, param.getMchKey());
        map.put("sign", sign);
        return WxUtil.getRequestXml(map);
    }
}
