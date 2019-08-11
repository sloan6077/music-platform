package com.sloan.music.platform.spider.service.core;

import lombok.Data;
import lombok.experimental.Accessors;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
@Data
@Accessors(chain = true)
public class SpiderContext {


    private Document document;

    private String url;

    private Map<String,String> header;

    private Map<String,String> param;

    private Map<String,String> formBody;

    private Request request;

    private Response response;
}
