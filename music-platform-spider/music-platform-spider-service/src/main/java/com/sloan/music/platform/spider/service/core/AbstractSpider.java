package com.sloan.music.platform.spider.service.core;

import com.sloan.music.platform.spider.service.http.HttpClient;
import com.sloan.music.platform.spider.service.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@Slf4j
public abstract class AbstractSpider implements ISpider {


    protected SpiderContext spiderContext;

    public AbstractSpider(SpiderContext spiderContext) {

        this.spiderContext = spiderContext;
    }

    @Override
    public void url(String url) {

        this.spiderContext.setUrl(url);
    }

    @Override
    public void header(Map<String,String> header) {

        this.spiderContext.setHeader(header);
    }

    @Override
    public void param(Map<String,String> param) {

        this.spiderContext.setParam(param);
    }

    public void spider() {


        log.info("url:{}",spiderContext.getUrl());
        log.info("param:{}",spiderContext.getParam());

        httpClient().doGetWithResultHandler(spiderContext.getUrl(), builder -> {
            Map<String,String> header = spiderContext.getHeader();
            if (!CollectionUtils.isEmpty(header)) {
                header.forEach(builder::setHeader);
            }
            Map<String,String> param = spiderContext.getParam();
            if (!CollectionUtils.isEmpty(param)) {
                param.forEach(builder::addQueryParam);
            }
        }, (req, res) -> {

            log.info("req:{}",req);

            spiderContext.setRequest(req);

            if (200 == res.getStatusCode()) {
                spiderContext.setDocument(Jsoup.parse(res.getResponseBody()));
            }
            spiderContext.setResponse(res);

            parser();

            send();
        });
    }

    private HttpClient httpClient() {

        return ApplicationContextUtil.getBean(HttpClient.class);
    }
}
