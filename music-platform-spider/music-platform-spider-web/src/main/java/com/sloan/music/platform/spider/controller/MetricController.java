package com.sloan.music.platform.spider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2020/1/3
 **/
@RestController
@RequestMapping("/metric")
@Slf4j
public class MetricController {


    @Resource
    @Qualifier("httpResultHandlerExecutor")
    private ExecutorService executorService;


    @RequestMapping(value = "/executor", method = RequestMethod.GET)
    public void executor() {

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)executorService;
        int activeCount = threadPoolExecutor.getActiveCount();
        int queueSize = threadPoolExecutor.getQueue().size();

        log.info("activeCount:{}",activeCount);
        log.info("queueSize:{}",queueSize);
    }
}
