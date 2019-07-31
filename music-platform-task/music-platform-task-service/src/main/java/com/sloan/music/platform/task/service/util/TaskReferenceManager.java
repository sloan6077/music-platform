package com.sloan.music.platform.task.service.util;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.config.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/11
 **/
@Slf4j
public class TaskReferenceManager {


    @Resource
    private ApplicationContext context;

    /**
     * 获取dubbo 接口代理
     *
     */
    public <T> T getDubboReference(Class<T> tClass) {
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setApplication(this.getApplicationConfig());
        reference.setProtocol("dubbo");

        reference.setCheck(true);

        MonitorConfig monitorConf = this.getMonitorConfig();
        if (monitorConf != null) {
            reference.setMonitor(monitorConf);
        }

        ConsumerConfig consumerConf = this.getConsumerConfig();
        if (consumerConf != null) {
            reference.setConsumer(consumerConf);
        }

        ModuleConfig moduleConf = this.getModuleConfig();
        if (moduleConf != null) {
            reference.setModule(moduleConf);
        }

        List<RegistryConfig> registryConfs = this.getRegistryConfigs();
        if (registryConfs.size() > 0) {
            reference.setRegistries(registryConfs);
        }

        reference.setVersion(Constants.ANY_VALUE);
        reference.setInterface(tClass);

        reference.setVersion("");
        reference.setCheck(false);
        reference.setTimeout(10000);

        return reference.get();
    }

    private <T> Map<String, T> getBeanConfMap(Class<T> type) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(context, type, false, false);
    }

    private ConsumerConfig getConsumerConfig() {
        ConsumerConfig consumerConfig = null;
        Map<String, ConsumerConfig> consumerConfigMap = getBeanConfMap(ConsumerConfig.class);
        for (ConsumerConfig config : consumerConfigMap.values()) {
            if (config.isDefault() == null || config.isDefault()) {
                if (consumerConfig != null) {
                    throw new IllegalStateException("Duplicate consumer configs: " + consumerConfig + " and " + config);
                }
                consumerConfig = config;
            }
        }
        return consumerConfig;
    }

    private ApplicationConfig getApplicationConfig() {
        ApplicationConfig applicationConfig = null;
        Map<String, ApplicationConfig> applicationConfigMap = getBeanConfMap(ApplicationConfig.class);

        for (ApplicationConfig config : applicationConfigMap.values()) {
            if (config.isDefault() == null || config.isDefault()) {
                if (applicationConfig != null) {
                    throw new IllegalStateException("Duplicate application configs: " + applicationConfig + " and " + config);
                }
                applicationConfig = config;
            }
        }
        return applicationConfig;
    }

    private ModuleConfig getModuleConfig() {
        ModuleConfig moduleConfig = null;
        Map<String, ModuleConfig> moduleConfigMap = getBeanConfMap(ModuleConfig.class);
        for (ModuleConfig config : moduleConfigMap.values()) {
            if (config.isDefault() == null || config.isDefault()) {
                if (moduleConfig != null) {
                    throw new IllegalStateException("Duplicate module configs: " + moduleConfig + " and " + config);
                }
                moduleConfig = config;
            }
        }
        return moduleConfig;
    }

    private MonitorConfig getMonitorConfig() {
        MonitorConfig monitorConfig = null;
        Map<String, MonitorConfig> monitorConfigMap = getBeanConfMap(MonitorConfig.class);
        for (MonitorConfig config : monitorConfigMap.values()) {
            if (config.isDefault() == null || config.isDefault()) {
                if (monitorConfig != null) {
                    throw new IllegalStateException("Duplicate monitor configs: " + monitorConfig + " and " + config);
                }
                monitorConfig = config;
            }
        }

        return monitorConfig;
    }

    private List<RegistryConfig> getRegistryConfigs() {
        List<RegistryConfig> registryConfigs = new ArrayList<>();
        Map<String, RegistryConfig> registryConfigMap = getBeanConfMap(RegistryConfig.class);
        for (RegistryConfig config : registryConfigMap.values()) {
            if (config.isDefault() == null || config.isDefault()) {
                registryConfigs.add(config);
            }
        }
        return registryConfigs;
    }
}
