package com.moyun.shop.pay.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description Properties工具类
 * @Author LiuJun
 * @Date 2020/5/24 2:50 下午
 */
public class PropertiesUtil {

    /**
     * 获取配置文件信息
     *
     * @param file
     * @return
     */
    public static Properties getProp(String file) {
        Properties props = new Properties();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        try (InputStream in = resourceLoader.getResource(file).getInputStream()) {
            props.load(in);
            return props;
        } catch (IOException e) {
            return props;
        }
    }
}
