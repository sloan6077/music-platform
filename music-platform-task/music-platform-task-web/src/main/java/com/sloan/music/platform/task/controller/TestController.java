package com.sloan.music.platform.task.controller;

import com.alibaba.dubbo.common.URL;
import com.sloan.music.platform.task.service.registry.ServiceFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
@RestController
@RequestMapping("/api/test")
public class TestController {


    @Resource
    private ServiceFilter serviceFilter;

    @RequestMapping( value = "/service", method = RequestMethod.GET)
    public List<URL> getService(String appName) {

        return serviceFilter.filterAppName(appName);
    }
}
