package com.moyun.shop.pay.wx.util;

import com.moyun.shop.pay.constants.PayConstants;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.util.Md5Util;
import com.moyun.shop.pay.wx.domain.result.RefundNotifyResult;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Base64;
import java.util.Map;

/**
 * @Description 退款通知工具类
 * @Author LiuJun
 * @Date 2020/5/24 1:39 下午
 */
@Slf4j
public class RefundNotifyUtil {

    private static boolean initialized = false;
    private static String algorithmModePadding = "AES/ECB/PKCS7Padding";
    private static String algorithm = "AES-256-ECB";

    /**
     * 获取通知结果
     *
     * @param request
     * @param mchKey  商户秘钥
     * @return
     */
    public static PayResult getNotify(HttpServletRequest request, String mchKey) {
        String reqRes = WxUtil.getNotifyXml(request);
        Map<String, String> reqMap = WxUtil.xmlParse(reqRes);
        // 获取return_code
        String returnCode = reqMap.get("return_code");
        if (!PayConstants.SUCCESS.equals(returnCode)) {
            // 获取错误信息
            String message = reqMap.get("return_msg");
            return new PayResult(message);
        }
        // 获取加密信息
        String reqInfo = decodeReqInfo(reqMap.get("req_info"), mchKey);
        Map<String, String> reqInfoMap = WxUtil.xmlParse(reqInfo);
        // 退款状态
        String status = reqInfoMap.get("refund_status");
        if (!PayConstants.SUCCESS.equals(status)) {
            log.info("微信退款通知失败-->通知内容：{}，状态码：{}", reqRes, status);
            return new PayResult(status);
        }
        RefundNotifyResult data = new RefundNotifyResult();
        // 微信支付订单号
        data.setTransactionId(reqInfoMap.get("transaction_id"));
        // 商户订单号
        data.setTradeNo(reqInfoMap.get("out_trade_no"));
        // 微信退款单号
        data.setRefundId(reqInfoMap.get("refund_id"));
        // 商户退款单号
        data.setRefundNo(reqInfoMap.get("out_refund_no"));
        // 订单金额
        data.setTotalFee(Integer.valueOf(reqInfoMap.get("total_fee")));
        // 申请退款金额
        data.setRefundFee(Integer.valueOf(reqInfoMap.get("refund_fee")));
        // 订单金额
        data.setSettlementRefundFee(Integer.valueOf(reqInfoMap.get("settlement_refund_fee")));
        // 退款成功时间
        data.setSuccessTime(reqInfoMap.get("success_time"));
        // 退款入账账户
        data.setRefundRecvAccount(reqInfoMap.get("refund_recv_accout"));
        // 退款资金来源
        data.setRefundAccount(reqInfoMap.get("refund_account"));
        // 退款发起来源
        data.setRefundRequestSource(reqInfoMap.get("refund_request_source"));
        return new PayResult(data);
    }

    /**
     * 解密
     *
     * @param reqInfo
     * @return
     */
    private static String decodeReqInfo(String reqInfo, String key) {
        initialize();
        String md5k = Md5Util.md5Encode(key, "UTF-8").toLowerCase();
        SecretKeySpec secretKey = new SecretKeySpec(md5k.getBytes(), algorithm);
        // 获取解码器实例，"BC"指定Java使用BouncyCastle库里的加/解密算法。
        try {
            Cipher cipher = Cipher.getInstance(algorithmModePadding, "BC");
            // 使用秘钥并指定为解密模式初始化解码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // cipher.doFinal(byte[] b)在单部分操作中加密或解密数据，或完成多部分操作。 根据此秘钥的初始化方式，对数据进行加密或解密。
            Base64.Decoder decoder = Base64.getDecoder();
            return new String(cipher.doFinal(decoder.decode(reqInfo)));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 安全提供者列表中注册解密算法提供者，这个加载过程还挺慢的，有时候要好几秒，只需要加载一次就能一直使用。
     */
    private static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
}
