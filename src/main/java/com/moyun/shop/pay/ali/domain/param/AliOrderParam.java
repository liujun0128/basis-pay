package com.moyun.shop.pay.ali.domain.param;

import com.moyun.shop.pay.wx.domain.param.BaseParam;
import lombok.Data;

/**
 * @Description 支付宝统一下单请求参数
 * @Author LiuJun
 * @Date 2020/5/19 4:34 下午
 */
@Data
public class AliOrderParam extends BaseParam {

    /**
     * 无参构造
     */
    public AliOrderParam() {
        super();
    }

    /**
     * 用户标识
     */
    private String openId;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    private String tradeNo;

    /**
     * 交易金额
     */
    private Integer totalFee;

    /**
     * 交易起始时间，格式为yyyyMMddHHmmss
     */
    private String startTime;

    /**
     * 交易结束时间，格式为yyyyMMddHHmmss
     * 建议：最短失效时间间隔大于1分钟
     */
    private String expireTime;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 指定支付方式
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    private String limitPay;
}
