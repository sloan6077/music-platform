package com.sloan.music.platform.spider.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
public class ApplicationContextUtil implements ApplicationContextAware {


    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        context = applicationContext;
    }

    public static <T> T getBean(String beanName,Class<T> t) {

        return context.getBean(beanName,t);
    }

    public static <T> T getBean(Class<T> t) {
        return  context.getBean(t);
    }

    public static String getActiveProfile() {

        return context.getEnvironment().getActiveProfiles()[0];
    }
}
