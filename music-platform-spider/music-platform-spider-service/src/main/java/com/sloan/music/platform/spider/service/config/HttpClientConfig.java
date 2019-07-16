package com.sloan.music.platform.spider.service.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.ManagedBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/15
 **/
@Configuration
@Slf4j
public class HttpClientConfig {

    private static final int EVENT_LOOP_GROUP_THREAD_NUM = 1;

    private static final int CONNECT_TIME_OUT_MILLISECONDS = 30000;
    private static final int REQUEST_TIME_OUT_MILLISECONDS = 30000;
    private static final int READ_TIME_OUT_MILLISECONDS = 10000;

    @Bean
    public AsyncHttpClientConfig asyncHttpClientConfig() {

        String osName = System.getProperty("os.name");
        log.warn("初始化asyncHttpClient配置，检测到环境为：" + osName);

        EventLoopGroup eventLoopGroup;
        if ("Linux".equalsIgnoreCase(osName)) {
            eventLoopGroup = new EpollEventLoopGroup();
        } else {
            eventLoopGroup = new NioEventLoopGroup(EVENT_LOOP_GROUP_THREAD_NUM);
        }
        return new DefaultAsyncHttpClientConfig
                .Builder()
                .setConnectTimeout(CONNECT_TIME_OUT_MILLISECONDS)
                .setRequestTimeout(REQUEST_TIME_OUT_MILLISECONDS)
                .setReadTimeout(READ_TIME_OUT_MILLISECONDS)
                .setEventLoopGroup(eventLoopGroup)
                .build();
    }

    @Bean
    public AsyncHttpClient asyncHttpClient(AsyncHttpClientConfig asyncHttpClientConfig) {

        return new DefaultAsyncHttpClient(asyncHttpClientConfig);
    }

    @Bean
    @Qualifier("httpResultHandlerExecutor")
    public ExecutorService executorService() {

        return new ThreadPoolExecutor(0, 64,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r,"http-client"));
    }
}
