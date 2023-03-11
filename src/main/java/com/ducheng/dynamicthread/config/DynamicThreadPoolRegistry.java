package com.ducheng.dynamicthread.config;

import com.alibaba.nacos.common.utils.StringUtils;
import com.ducheng.dynamicthread.queue.VariableLinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class DynamicThreadPoolRegistry {

    //这里就不用多说了，项目中所有配置的线程池都会注入到这个Map结构中，以线程池的名称为key
    @Resource
    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    public void refresher(DynamicThreadPoolProperties dtpProperties) {
        if (Objects.isNull(dtpProperties) || CollectionUtils.isEmpty(dtpProperties.getExecutors())) {
            log.warn("DynamicTp refresh, empty threadPoolProperties.");
            return;
        }
        //遍历Map,给线程池重新设置值。
        dtpProperties.getExecutors().forEach(x -> {
            if (StringUtils.isBlank(x.getThreadPoolName())) {
                log.warn("DynamicTp refresh, threadPoolName must not be empty.");
                return;
            }
            ThreadPoolExecutor executor = threadPoolExecutorMap.get(x.getThreadPoolName());
            //具体的设置方法
            refresher(executor, x);
        });
    }

    private void refresher(ThreadPoolExecutor executor, ThreadPoolProperties properties) {
        if (properties.getCorePoolSize() < 0 ||
                properties.getMaximumPoolSize() <= 0 ||
                properties.getMaximumPoolSize() < properties.getCorePoolSize() ||
                properties.getKeepAliveTime() < 0) {

            log.error("DynamicTp refresh,  invalid configuration: {}", properties);
            return;
        }

        doRefresher(executor, properties);
    }

    private void doRefresher(ThreadPoolExecutor executor, ThreadPoolProperties properties) {

        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaximumPoolSize(properties.getMaximumPoolSize());
        executor.setKeepAliveTime(properties.getKeepAliveTime(), properties.getTimeUnit());
        executor.allowCoreThreadTimeOut(properties.isAllowCoreThreadTimeOut());
        val blockingQueue = executor.getQueue();
        if (blockingQueue instanceof LinkedBlockingQueue) {
            log.warn("DynamicTp refresh, {} :capacity cannot be changed.", blockingQueue);
        }
        if (blockingQueue instanceof VariableLinkedBlockingQueue) {
            ((VariableLinkedBlockingQueue<Runnable>) blockingQueue).setCapacity(properties.getQueueCapacity());
        }
    }
}

