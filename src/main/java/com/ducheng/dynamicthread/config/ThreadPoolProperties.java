package com.ducheng.dynamicthread.config;

import com.ducheng.dynamicthread.consts.DynamicThreadPoolConst;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class ThreadPoolProperties {

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 最大线程数
     */
    private int maximumPoolSize = DynamicThreadPoolConst.AVAILABLE_PROCESSORS;

    /**
     * 线程队列数
     */
    private int queueCapacity = 1024;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 超时时间
     */
    private long keepAliveTime = 30;

    /**
     * Timeout unit.
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;
}
