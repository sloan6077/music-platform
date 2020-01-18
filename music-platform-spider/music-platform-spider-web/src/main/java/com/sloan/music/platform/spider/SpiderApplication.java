package com.sloan.music.platform.spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/15
 **/
@SpringBootApplication(scanBasePackages = "com.sloan.music.platform.spider")
@MapperScan("com.sloan.music.platform.spider.dao")
public class SpiderApplication {


    public static void main(String[] args) {

        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SpiderApplication.class, args);
    }
}
