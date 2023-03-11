package com.ducheng.dynamicthread.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "spring.dynamic.tp")
public class DynamicThreadPoolProperties {

    /**
     * 是否开始动态配置
     */
    private boolean enabled = true;

    /**
     * 线程池基础配置
     */
    private List<ThreadPoolProperties> executors;

}
