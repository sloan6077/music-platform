package com.sloan.music.platform.task.service.registry.config;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.sloan.music.platform.task.service.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Arrays;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
@Configuration
@Slf4j
public class ConfigCenter {

    @Value("${admin.config-center:}")
    private String configCenter;

    @Value("${admin.registry.address:}")
    private String registryAddress;

    @Value("${admin.metadata-report.address:}")
    private String metadataAddress;

    @Value("${admin.registry.group:dubbo}")
    private String registryGroup;

    @Value("${admin.config-center.group:dubbo}")
    private String configCenterGroup;

    @Value("${admin.metadata-report.group:dubbo}")
    private String metadataGroup;

    private URL configCenterUrl;
    private URL registryUrl;
    private URL metadataUrl;

    @Bean("governanceConfiguration")
    GovernanceConfiguration getDynamicConfiguration() {
        GovernanceConfiguration dynamicConfiguration = null;

        if (StringUtils.isNotEmpty(configCenter)) {
            configCenterUrl = formUrl(configCenter, configCenterGroup);
            dynamicConfiguration = ExtensionLoader.getExtensionLoader(GovernanceConfiguration.class).getExtension(configCenterUrl.getProtocol());
            dynamicConfiguration.setUrl(configCenterUrl);
            dynamicConfiguration.init();
            String config = dynamicConfiguration.getConfig(Constants.GLOBAL_CONFIG_PATH);

            if (StringUtils.isNotEmpty(config)) {
                Arrays.stream(config.split("\n")).forEach(s -> {
                    if(s.startsWith(Constants.REGISTRY_ADDRESS)) {
                        String registryAddress = s.split("=")[1].trim();
                        registryUrl = formUrl(registryAddress, configCenterGroup);
                    } else if (s.startsWith(Constants.METADATA_ADDRESS)) {
                        metadataUrl = formUrl(s.split("=")[1].trim(), configCenterGroup);
                    }
                });
            }
        }
        if (dynamicConfiguration == null) {
            if (StringUtils.isNotEmpty(registryAddress)) {
                registryUrl = formUrl(registryAddress, registryGroup);
                dynamicConfiguration = ExtensionLoader.getExtensionLoader(GovernanceConfiguration.class).getExtension(registryUrl.getProtocol());
                dynamicConfiguration.setUrl(registryUrl);
                dynamicConfiguration.init();
                log.warn("you are using dubbo.registry.address, which is not recommend, please refer to: https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
            } else {
                throw new RuntimeException("Either config center or registry address is needed, please refer to https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
                //throw exception
            }
        }
        return dynamicConfiguration;
    }

    @Bean
    @DependsOn("governanceConfiguration")
    Registry getRegistry() {
        Registry registry = null;
        if (registryUrl == null) {
            if (StringUtils.isBlank(registryAddress)) {
                throw new RuntimeException("Either config center or registry address is needed, please refer to https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
            }
            registryUrl = formUrl(registryAddress, registryGroup);
        }
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        registry = registryFactory.getRegistry(registryUrl);
        return registry;
    }

    /*
     * generate metadata client
     */
    @Bean
    @DependsOn("governanceConfiguration")
    MetaDataCollector getMetadataCollector() {
        MetaDataCollector metaDataCollector = new NoOpMetadataCollector();
        if (metadataUrl == null) {
            if (StringUtils.isNotEmpty(metadataAddress)) {
                metadataUrl = formUrl(metadataAddress, metadataGroup);
            }
        }
        if (metadataUrl != null) {
            metaDataCollector = ExtensionLoader.getExtensionLoader(MetaDataCollector.class).getExtension(metadataUrl.getProtocol());
            metaDataCollector.setUrl(metadataUrl);
            metaDataCollector.init();
        } else {
            log.warn("you are using dubbo.registry.address, which is not recommend, please refer to: https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
        }
        return metaDataCollector;
    }

    private URL formUrl(String config, String group) {
        URL url = URL.valueOf(config);
        if (StringUtils.isNotEmpty(group)) {
            url = url.addParameter(Constants.GROUP_KEY, group);
        }
        return url;
    }

}
