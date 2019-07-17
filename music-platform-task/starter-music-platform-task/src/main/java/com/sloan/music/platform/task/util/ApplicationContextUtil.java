package com.sloan.music.platform.task.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        context = appContext;
    }

    /**
     * for junit test
     * @param appContext appContext
     */
    public static void injectApplicationContext(ApplicationContext appContext) {
        context = appContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }


    public static <T> T getBean(Class<T> t) {
        return  context.getBean(t);
    }

    public static <T> T getBean(String beanName,Class<T> t) {

        return context.getBean(beanName,t);
    }

    public static Map<String,Object> getMapbeanwithAnnotion(Class<? extends Annotation> cls) {

        return context.getBeansWithAnnotation(cls);
    }
}
