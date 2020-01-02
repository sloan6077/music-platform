package com.sloan.music.platform.spider.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@SpringBootApplication(scanBasePackages = {"com.sloan.music.platform.spider"})
@Import({DatasourceConfigurationTest.class})
public class TestApplication {


}
