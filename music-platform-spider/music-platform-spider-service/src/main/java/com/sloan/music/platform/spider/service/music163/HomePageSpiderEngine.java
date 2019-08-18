package com.sloan.music.platform.spider.service.music163;

import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.kafka.KafkaService;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
@Slf4j
public class HomePageSpiderEngine {

    @Resource
    private KafkaService kafkaService;

    //order=hot&cat=%E5%85%A8%E9%83%A8
    private static final String PRE_URL = "https://music.163.com/discover/playlist/";


    private static final int PLAY_LIST_SIZE = 38;

    public void start() {

        for (int i=1;i<PLAY_LIST_SIZE;i++) {

            Map<String,String> param = new HashMap<>();
            param.put("order","hot");
            param.put("cat","全部");
            param.put("limit","35");
            if (i != 0) {
                param.put("offset",String.valueOf(i*35));
            }

            HomePagePlayListSpider homePagePlayListSpider = new HomePagePlayListSpider(new SpiderContext<>(),param);
            homePagePlayListSpider.spider();
        }
    }

    private class HomePagePlayListSpider extends AbstractSpider<String,Void> {

        private static final String PLAY_LIST_PRE_URL = "https://music.163.com/#";

        HomePagePlayListSpider(SpiderContext<String,Void> spiderContext, Map<String, String> param) {

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

            Document document = spiderContext.getDocument();
            Elements elements = document.getElementsByClass("msk");

            List<String> messages = new ArrayList<>();
            elements.forEach(w -> messages.add(PLAY_LIST_PRE_URL + w.attr("href")));

            spiderContext.setMessages(messages);
        }

        @Override
        public void send() {

            List<String> messages = spiderContext.getMessages();
            if (!CollectionUtils.isEmpty(messages)) {
                messages.forEach(message -> kafkaService.sendMessage(TopicConstants.Music163PlayList.TOPIC_PLAYLIST, message));
            }
        }
    }
}
