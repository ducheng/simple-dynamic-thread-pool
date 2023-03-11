package com.ducheng.dynamicthread.config;


import com.ducheng.dynamicthread.queue.VariableLinkedBlockingQueue;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class ThreadPoolConfiguration {

    /**
     * Default inner thread factory.
     */
    private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("dy-thread-%d").build();

    /**
     * 拒绝策略
     */
    private final RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();

    @Bean
    public ThreadPoolExecutor commonExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }

    @Bean
    public ThreadPoolExecutor dynamicThreadPoolExecutor1() {
        ThreadPoolProperties defaultThreadPoolProperties = new ThreadPoolProperties();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                defaultThreadPoolProperties.getCorePoolSize(),
                defaultThreadPoolProperties.getMaximumPoolSize(),
                defaultThreadPoolProperties.getKeepAliveTime(),
                defaultThreadPoolProperties.getTimeUnit(),
                new VariableLinkedBlockingQueue<>(defaultThreadPoolProperties.getQueueCapacity()),
                namedThreadFactory,
                rejectedExecutionHandler
        );
        executor.allowCoreThreadTimeOut(defaultThreadPoolProperties.isAllowCoreThreadTimeOut());
        return executor;
    }
}

