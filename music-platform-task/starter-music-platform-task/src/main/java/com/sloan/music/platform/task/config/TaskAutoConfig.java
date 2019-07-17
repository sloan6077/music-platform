package com.sloan.music.platform.task.config;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Configuration
@EnableConfigurationProperties(TaskProperties.class)
public class TaskAutoConfig {

    @Resource
    private TaskProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @Qualifier("taskExecutor")
    public ExecutorService executorService() {


        return Executors.newFixedThreadPool(properties.getThreadNum(), new NamedThreadFactory("任务执行!"));
    }

}
