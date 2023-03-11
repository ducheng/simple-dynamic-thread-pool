package com.ducheng.dynamicthread.config;

import com.alibaba.nacos.common.utils.StringUtils;
import com.ducheng.dynamicthread.utils.YamlConfigParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
public abstract class AbstractRefresher {

    @Resource
    private DynamicThreadPoolRegistry dynamicThreadPoolRegistry;

    //项目启动之初就与.yml文件中的配置项进行映射了
    @Resource
    private DynamicThreadPoolProperties dtpProperties;

    public void refresher(String content) {
        if (StringUtils.isBlank(content)) {
            log.warn("DynamicTp refresh, empty content.");
            return;
        }
        //目前先支持yml解析
        YamlConfigParser yamlParser = new YamlConfigParser();
        Map<Object, Object> yamlMap = yamlParser.doParse(content);
        doRefresher(yamlMap);
    }

    protected void doRefresher(Map<Object, Object> properties) {
        if (ObjectUtils.isEmpty(properties)) {
            log.warn("DynamicTp refresh, empty properties.");
            return;
        }
        PropertiesBinder.bindDtpProperties(properties,dtpProperties);
        doRefresher(dtpProperties);
    }

    protected void doRefresher(DynamicThreadPoolProperties dtpProperties) {

        dynamicThreadPoolRegistry.refresher(dtpProperties);
    }
}


