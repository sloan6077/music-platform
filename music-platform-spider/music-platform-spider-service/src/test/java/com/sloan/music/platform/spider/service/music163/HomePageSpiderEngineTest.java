package com.sloan.music.platform.spider.service.music163;

import com.sloan.music.platform.spider.service.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
public class HomePageSpiderEngineTest extends BaseTest {

    @Resource
    private HomePageSpiderEngine homePageSpiderEngine;

    @Test
    @SneakyThrows
    public void test_start() {

        homePageSpiderEngine.start();

        Thread.sleep(100*1000);
    }
}
