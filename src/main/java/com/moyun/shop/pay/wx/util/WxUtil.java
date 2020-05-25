package com.moyun.shop.pay.wx.util;

import com.moyun.shop.pay.constants.PayConstants;
import com.moyun.shop.pay.domain.result.PayResult;
import com.moyun.shop.pay.util.Md5Util;
import com.moyun.shop.pay.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Description 微信支付工具类
 * @Author LiuJun
 * @Date 2020/5/20 5:03 下午
 */
@Slf4j
public class WxUtil {

    /**
     * 获取错误信息
     *
     * @param map
     * @return
     */
    public static PayResult getMessage(Map<String, String> map) {
        // 获取return_code
        String returnCode = map.get("return_code");
        if (!PayConstants.SUCCESS.equals(returnCode)) {
            // 获取错误信息
            String message = map.get("return_msg");
            return new PayResult(message);
        }
        // 获取result_code
        String resultCode = map.get("result_code");
        if (!PayConstants.SUCCESS.equals(resultCode)) {
            // 获取错误信息
            String errCode = map.get("err_code");
            String message = map.get("err_code_des");
            return new PayResult(errCode, message);
        }
        return new PayResult();
    }

    /**
     * 获取签名
     *
     * @param characterEncoding 编码
     * @param map               参数
     * @param key               商户平台设置的密钥key
     * @return
     */
    public static String getSign(String characterEncoding, Map<String, Object> map, String key) {
        StringBuffer sb = new StringBuffer();
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=" + key);
        return Md5Util.md5Encode(sb.toString(), characterEncoding).toUpperCase();
    }

    /**
     * 获取请求Xml
     *
     * @param map
     * @return
     */
    public static String getRequestXml(SortedMap<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = map.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 获取通知Xml
     *
     * @param request
     * @return
     */
    public static String getNotifyXml(HttpServletRequest request) {
        try (ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
             InputStream inputStream = request.getInputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return new String(outSteam.toByteArray(), "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 解析返回xml
     *
     * @param xml
     * @return
     */
    public static Map<String, String> xmlParse(String xml) {
        Map<String, String> result = new HashMap(16);
        xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (PayUtil.isEmpty(xml)) {
            return result;
        }
        try (InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"))) {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v;
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(children);
                }
                result.put(k, v);
            }
            return result;
        } catch (Exception e) {
            log.info("微信xml解析异常-->{}", e);
            return result;
        }
    }

    /**
     * 获取子节点
     *
     * @param children
     * @return
     */
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }
}
