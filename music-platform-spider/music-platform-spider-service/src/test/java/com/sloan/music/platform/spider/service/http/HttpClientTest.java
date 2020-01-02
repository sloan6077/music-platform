package com.sloan.music.platform.spider.service.http;

import com.sloan.music.platform.spider.service.BaseTest;
import javafx.util.Pair;
import lombok.SneakyThrows;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
public class HttpClientTest extends BwaseTest {

    @Resource
    private HttpClient httpClient;

    @Test
    @SneakyThrows
    public void testGET() {

        httpClient.doGetWithResultHandler("https://music.163.com/discover/playlist/", builder -> {
            builder.setHeader("Referer", "http://music.163.com/");
            builder.setHeader("Host", "music.163.com");
            builder.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

            builder.addQueryParam("order", "hot");
            builder.addQueryParam("cat", "全部");
            builder.addQueryParam("limit", "35");
        }, (req, res) -> {
            System.out.println("请求数据：" + req);
            System.out.println("响应数据：" + res);
        });


        Thread.sleep(3*1000);

    }

    @SneakyThrows
    @Test
    public void testPOST() {

        httpClient.doPostWithResultHandler("https://music.163.com/weapi/v1/resource/comments/R_SO_4_1376091684?csrf_token=", builder -> {
            builder.setHeader("Referer", "http://music.163.com/");
            builder.setHeader("Host", "music.163.com");
            builder.setHeader("content-type","application/x-www-form-urlencoded");
            builder.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");

            builder.addFormParam("params","UaQJv0cjDDaY+Mwjqn+6PPfYcksvVph1Rw3Zmqs0nxJPNSW8P0RQGfbV3fLhhWKLElSUiXcV/YT7RIhNdEJBsRcnOLWAl/E3BwVT2MalIvDHWoE+1DAS9sZYHFEM8kTujHrpLi+sD/J2sjhWxztpH2x1/LJVtL4j2tC2qAXAlLgNn3kSJA4q1oHly7zXIjGV");
            builder.addFormParam("encSecKey","7ec0c896e5590304ac276391107fe05c45d810d12821b254523392b7b819b56d7b71284964b30ad09f842639381f1aa850023b050505c260518d3954e08e9134dee7010842d4acc953803c0a3a6914dd21037c1c192c1fc858c5192e49b5da41fc155de19483bf72cce4369df57b9c25c4c6dfed60a0a036f067dd49a207092e");

        }, (req, res) -> {
            System.out.println("请求数据：" + req);
            System.out.println("响应数据：" + res);
        });

        Thread.sleep(3*1000);
    }
}
