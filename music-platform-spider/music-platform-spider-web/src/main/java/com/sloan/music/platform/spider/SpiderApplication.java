package com.sloan.music.platform.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/15
 **/
@SpringBootApplication(scanBasePackages = "com.sloan.music.platform.spider")
public class SpiderApplication {


    public static void main(String[] args) {

        SpringApplication.run(SpiderApplication.class, args);
    }
}
