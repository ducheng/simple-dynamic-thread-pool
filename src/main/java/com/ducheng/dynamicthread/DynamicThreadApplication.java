package com.ducheng.dynamicthread;

import com.ducheng.dynamicthread.config.DynamicThreadPoolProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DynamicThreadPoolProperties.class)
public class DynamicThreadApplication {


    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadApplication.class
        );
    }
}
