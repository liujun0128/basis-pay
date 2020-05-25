package com.moyun.shop.pay.wx.constants;

/**
 * @Description 支付常量
 * @Author LiuJun
 * @Date 2020/5/24 3:50 下午
 */
public class WxConstants {

    /**
     * 公共URL
     */
    private static final String BASE_URL = "https://api.mch.weixin.qq.com/pay";

    /**
     * 统一下单URL
     */
    public static final String UNIFIED_ORDER_URL = BASE_URL + "/unifiedorder";

    /**
     * 订单查询URL
     */
    public static final String ORDER_QUERY_URL = BASE_URL + "/orderquery";

    /**
     * 关闭订单URL
     */
    public static final String ORDER_CLOSE_URL = BASE_URL + "/closeorder";

    /**
     * 申请退款URL
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 查询退款URL
     */
    public static final String REFUND_QUERY_URL = BASE_URL + "/refundquery";
}
