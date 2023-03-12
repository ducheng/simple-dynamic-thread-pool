package com.ducheng.dynamicthread.config;


import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
/**
 *  nacos De监听器，监听nacos 的变化
 */

@Component
@Slf4j
public class NacosRefresher extends AbstractRefresher  implements ApplicationListener<EnvironmentChangeEvent> {

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent environmentChangeEvent) {
        ConfigService configService = null;
        try {
            configService = NacosFactory.createConfigService(nacosConfigProperties.assembleConfigServiceProperties());
            String config = configService.getConfig(serviceName, nacosConfigProperties.getGroup(),1000);
            refresher(config);
            log.info("打印参数：{}",config);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
