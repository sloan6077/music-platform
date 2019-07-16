package com.sloan.music.platform.spider.service.http;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@Component
@Slf4j
public class HttpClient {


    @Resource
    private AsyncHttpClient asyncHttpClient;


    private static final ExecutorService RESULT_HANDLER_EXECUTOR = new ThreadPoolExecutor(0, 64,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r,"http-client"));


    public Pair<Request, Response> doGet(String url) {
        return doGet(url, null);
    }
    /**
     * GET阻塞请求，直到响应数据
     *
     * @param url        请求地址
     * @param preRequest 预请求信息，如：参数、头、请求体等
     *
     * @return 响应数据
     */
    public Pair<Request, Response> doGet(String url, Consumer<BoundRequestBuilder> preRequest) {
        Pair<Request, ListenableFuture<Response>> result = doGetInFuture(url, preRequest);
        return new Pair<>(result.getKey(), futureGet(result.getValue()));
    }
    /**
     * GET异步请求，有响应数据时会自动调用resultHandler进入处理
     *
     * @param url           请求地址
     * @param resultHandler 响应数据回调处理函数
     */
    public void doGetWithResultHandler(String url, BiConsumer<Request, Response> resultHandler) {
        doGetWithResultHandler(url, null, resultHandler);
    }
    /**
     * GET异步请求，有响应数据时会自动调用resultHandler进入处理
     *
     * @param url           请求地址
     * @param preRequest    预请求信息，如：参数、头、请求体等
     * @param resultHandler 响应数据回调处理函数
     */
    public void doGetWithResultHandler(String url, Consumer<BoundRequestBuilder> preRequest, BiConsumer<Request, Response> resultHandler) {
        Pair<Request, ListenableFuture<Response>> result = doGetInFuture(url, preRequest);
        addListener(resultHandler, result.getKey(), result.getValue());
    }
    /**
     * GET异步请求，可批量发送请求，然后对Future集中处理
     *
     * @param url 请求地址
     *
     * @return
     */
    public ListenableFuture<Response> doGetInFuture(String url) {
        return doGetInFuture(url, null).getValue();
    }
    /**
     * GET异步请求，可批量发送请求，然后对Future集中处理
     *
     * @param url        请求地址
     * @param preRequest 预请求信息，如：参数、头、请求体等
     *
     * @return key: 请求参数，val：响应数据引用，可通过get()方法获取响应数据（抛异常时为null）
     */
    public Pair<Request, ListenableFuture<Response>> doGetInFuture(String url, Consumer<BoundRequestBuilder> preRequest) {
        if (url == null) {
            return null;
        }
        BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
        if (preRequest != null) {
            preRequest.accept(builder);
        }
        return new Pair<>(builder.build(), builder.execute());
    }
    /**
     * POST阻塞请求，直到响应数据
     *
     * @param url        请求地址
     * @param preRequest 预请求信息，如：参数、头、请求体等
     *
     * @return 响应数据
     */
    public Pair<Request, Response> doPost(String url, Consumer<BoundRequestBuilder> preRequest) {
        Pair<Request, ListenableFuture<Response>> result = doPostInFuture(url, preRequest);
        return new Pair<>(result.getKey(), futureGet(result.getValue()));
    }
    /**
     * POST异步请求，有响应数据时会自动调用resultHandler进入处理
     *
     * @param url           请求地址
     * @param preRequest    预请求信息，如：参数、头、请求体等
     * @param resultHandler 响应数据回调处理函数
     */
    public void doPostWithResultHandler(String url, Consumer<BoundRequestBuilder> preRequest, BiConsumer<Request, Response> resultHandler) {
        Pair<Request, ListenableFuture<Response>> result = doPostInFuture(url, preRequest);
        addListener(resultHandler, result.getKey(), result.getValue());
    }
    /**
     * POST异步请求，可批量发送请求，然后对Future集中处理
     *
     * @param url        请求地址
     * @param preRequest 预请求信息，如：参数、头、请求体等
     *
     * @return key: 请求参数，val：响应数据引用，可通过get()方法获取响应数据（抛异常时为null）
     */
    public Pair<Request, ListenableFuture<Response>> doPostInFuture(String url, Consumer<BoundRequestBuilder> preRequest) {
        if (url == null) {
            return null;
        }
        BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        if (preRequest != null) {
            preRequest.accept(builder);
        }
        return new Pair<>(builder.build(), builder.execute());
    }
    private Response futureGet(ListenableFuture<Response> future) {
        if (future == null) {
            return null;
        }
        try {
            return future.get();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
    private void addListener(BiConsumer<Request, Response> resultHandler, Request preRequest, ListenableFuture<Response> future) {
        if (future == null || resultHandler == null) {
            return;
        }
        future.addListener(() -> {
            try {
                resultHandler.accept(preRequest, future.get());
            } catch (Exception e) {
                resultHandler.accept(preRequest, null);
                log.error("", e);
            }
        }, RESULT_HANDLER_EXECUTOR);
    }
}
