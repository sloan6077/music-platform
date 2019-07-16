package com.sloan.music.platform.spider.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Test
    public void hello() {
        String hello = "hello world";
        Assert.assertNotNull(hello);
    }
}
