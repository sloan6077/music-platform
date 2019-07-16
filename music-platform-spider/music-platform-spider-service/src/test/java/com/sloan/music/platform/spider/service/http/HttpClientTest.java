package com.sloan.music.platform.spider.service.http;

import javafx.util.Pair;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
public class HttpClientTest {

    @Resource
    private HttpClient httpClient;

    @Test
    public void test() {


        /* 阻塞 */
        Pair<Request, Response> res1 = httpClient.doGet("https://localhost:8080/test?a=aaa&b=bbb");
        System.out.println("响应数据：" + res1.getValue().getResponseBody());
        Pair<Request, Response> res2 = httpClient.doGet("https://localhost:8080/test", builder -> {
            builder.setHeader("headInfo", "val");
            builder.addQueryParam("param", "paramVal");
        });
        System.out.println("响应数据：" + res2.getValue());
        /* 异步回调处理 */
        httpClient.doGetWithResultHandler("https://localhost:8080/test", builder -> {
            builder.setHeader("headInfo", "val");
            builder.addQueryParam("param", "paramVal");
        }, (req, res) -> {
            System.out.println("请求数据：" + req);
            System.out.println("响应数据：" + res);
        });
        /* 异步Future */
        List<ListenableFuture<Response>> futureList = new ArrayList<>(128);
        for (int i = 0; i < 100; i++) {
            futureList.add(httpClient.doGetInFuture("https://localhost:8080/test?a=aaa&b=bbb"));
        }
        // TODO 这里可以做其它事情
        futureList.forEach(future -> {
            // 批量处理响应数据
            try {
                System.out.println("响应数据：" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
