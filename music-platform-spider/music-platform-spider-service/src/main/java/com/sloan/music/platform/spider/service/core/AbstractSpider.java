package com.sloan.music.platform.spider.service.core;

import com.alibaba.fastjson.JSON;
import com.sloan.music.platform.spider.service.http.HttpClient;
import com.sloan.music.platform.spider.service.util.ApplicationContextUtil;
import com.sloan.music.platform.spider.service.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@Slf4j
public abstract class AbstractSpider<C,B> implements ISpider {

    private static final String CONTENT_TYPE = "content-type";

    private static final String FORM_DATA = "application/x-www-form-urlencoded";

    protected SpiderContext<C,B> spiderContext;

    public AbstractSpider(SpiderContext<C,B> spiderContext) {

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

    @Override
    public void formBody(Map<String,String> formBody) {

        this.spiderContext.setFormBody(formBody);
    }

    public void spider() {

        httpClient().doGetWithResultHandler(spiderContext.getUrl(), builder -> {
            Map<String,String> header = spiderContext.getHeader();
            if (!CollectionUtils.isEmpty(header)) {
                header.forEach(builder::setHeader);
            }
            Map<String,String> param = spiderContext.getParam();
            if (!CollectionUtils.isEmpty(param)) {
                param.forEach(builder::addQueryParam);
            }
            Map<String,String> formBody = spiderContext.getFormBody();
            if (!CollectionUtils.isEmpty(formBody)) {
                builder.setHeader(CONTENT_TYPE,FORM_DATA);

            }

        }, (req, res) -> {

            log.info("req:{}",req);

            spiderContext.setRequest(req);

            if (200 == res.getStatusCode()) {
                spiderContext.setDocument(Jsoup.parse(res.getResponseBody()));
            }
            spiderContext.setResponse(res);

            parser();
            business();
            send();
        });
    }

    private static final String SCRIPT_TYPE = "application/ld+json";

    protected Date getPubDate(Document document) {

        Elements elements = document.getElementsByTag("script");
        for (Element element : elements) {
            String scriptType = element.attr("type");
            if (scriptType.equals(SCRIPT_TYPE)) {
                String dateStr  = JSON.parseObject(element.childNode(0).outerHtml()).getString("pubDate");
                return DateUtil.parse(dateStr);
            }
        }

        return new Date();
    }

    private HttpClient httpClient() {

        return ApplicationContextUtil.getBean(HttpClient.class);
    }
}
