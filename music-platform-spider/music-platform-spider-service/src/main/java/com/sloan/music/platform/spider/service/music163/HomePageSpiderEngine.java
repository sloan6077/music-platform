package com.sloan.music.platform.spider.service.music163;

import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
@Slf4j
public class HomePageSpiderEngine {


    //order=hot&cat=%E5%85%A8%E9%83%A8
    private static final String PRE_URL = "https://music.163.com/discover/playlist/";


    private static final int PLAY_LIST_SIZE = 38;

    public void start() {

        for (int i=37;i<PLAY_LIST_SIZE;i++) {

            Map<String,String> param = new HashMap<>();
            param.put("order","hot");
            param.put("cat","全部");
            param.put("limit","35");
            if (i != 0) {
                param.put("offset",String.valueOf(i*35));
            }

            HomePageSpider homePageSpider = new HomePageSpider(new SpiderContext(),param);
            homePageSpider.spider();
        }
    }

    private class HomePageSpider extends AbstractSpider {


        public HomePageSpider(SpiderContext spiderContext,Map<String,String> param) {

            super(spiderContext);

            url(PRE_URL);
            Map<String,String> header = new HashMap<>();
            header.put(HeaderKeyConstants.REFERER,"http://music.163.com/");
            header.put(HeaderKeyConstants.HOST,"music.163.com");
            header.put(HeaderKeyConstants.USER_AGENT, UserAgentConstants.CHROME_AGENT);
            header(header);
            param(param);
        }

        @Override
        public void parser() {

            log.info("parser:{}","123");

            Document document = spiderContext.getDocument();
            Elements elements = document.getElementsByClass("msk");
            elements.forEach(w -> {
                log.info("href:{}",w.attr("href"));
                log.info("title:{}",w.attr("title"));
            });
        }

        @Override
        public void send() {


        }
    }
}
