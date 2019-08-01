package com.sloan.music.platform.task.service.registry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.sloan.music.platform.task.service.util.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
@Component
public class ServiceFilter {

    private static final String TASK_REMOTE_SERVICE = "com.sloan.music.platform.task.api.service.TaskRemoteService";

    private static final Protocol REF_PROTOCOL = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory PROXY_FACTORY = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    @Resource
    private RegistryServerSync registryServerSync;

    public List<URL> filterAppName(String appName) {

        ConcurrentMap<String, ConcurrentMap<String, Map<String, URL>>> map = registryServerSync.getRegistryCache();

        ConcurrentMap<String, Map<String, URL>> providerServiceMap = map.get(Constants.PROVIDERS_CATEGORY);
        Map<String, URL> serviceMap = providerServiceMap.get(TASK_REMOTE_SERVICE);
        Set<String> keySet = serviceMap.keySet();
        return keySet.stream()
                .filter(key -> appName.equals(serviceMap.get(key).getParameters().get("application")))
                .map(serviceMap::get)
                .collect(Collectors.toList());
    }

    public <T> T getByURL(Class<T> serviceClass,URL url) {

        Invoker<T> invoker = REF_PROTOCOL.refer(serviceClass, url);
        return PROXY_FACTORY.getProxy(invoker);
    }


}
