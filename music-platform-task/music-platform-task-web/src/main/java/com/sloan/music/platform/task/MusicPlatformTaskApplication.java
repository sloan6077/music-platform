package com.sloan.music.platform.task;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/20
 **/
@SpringBootApplication
@MapperScan("com.sloan.music.platform.task.dao.mapper")
public class MusicPlatformTaskApplication {

    public static void main(String[] args) {

        SpringApplication.run(MusicPlatformTaskApplication.class,args);
    }
}
