package com.ducheng.dynamicthread.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 加载配置文件的工具类
 */
public class YamlConfigParser {
    public Map<Object, Object> doParse(String content) {

        if (StringUtils.isEmpty(content)) {
            return Collections.emptyMap();
        }
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ByteArrayResource(content.getBytes()));
        return bean.getObject();
    }
}
