package com.sloan.music.platform.spider.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.sloan.music.platform.spider.dao"})
public class DatasourceConfigurationTest {

}
