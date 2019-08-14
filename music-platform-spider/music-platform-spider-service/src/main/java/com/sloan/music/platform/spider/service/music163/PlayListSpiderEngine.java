package com.sloan.music.platform.spider.service.music163;

import com.alibaba.fastjson.JSON;
import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.entity.music163.bo.Music163PlayListBO;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import com.sloan.music.platform.spider.service.util.DateUtil;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/13
 **/
@Component
@Slf4j
public class PlayListSpiderEngine {


    @KafkaListener(topics = {TopicConstants.MUSIC163_PLAYLIST},groupId = "play_list_spider_engine")
    public void processMessage(ConsumerRecord<String, String> record) {

        String playListId = record.value();
        PlayListSpider playListSpider = new PlayListSpider(new SpiderContext<>(),playListId);
        playListSpider.spider();
    }

    public void test(String playListId) {

        PlayListSpider playListSpider = new PlayListSpider(new SpiderContext<>(),playListId);
        playListSpider.spider();
    }

    private class PlayListSpider extends AbstractSpider<String, Music163PlayListBO> {

        private static final String PLAY_LIST_PRE_URL = "https://music.163.com/playlist?id=";

        private Long id;

        PlayListSpider(SpiderContext<String, Music163PlayListBO> spiderContext, String playListId) {

            super(spiderContext);

            this.id = Long.valueOf(playListId);

            Map<String,String> header = new HashMap<>();
            header.put(HeaderKeyConstants.REFERER,"http://music.163.com/");
            header.put(HeaderKeyConstants.HOST,"music.163.com");
            header.put(HeaderKeyConstants.USER_AGENT, UserAgentConstants.CHROME_AGENT);
            header(header);
            url(PLAY_LIST_PRE_URL + playListId);
        }

        private static final String TITLE_KEY = "og:title";

        private static final String IMAGE_URL_KEY = "og:image";

        private static final String DESCRIPTION_KEY = "og:description";

        private static final String SCRIPT_TYPE = "application/ld+json";

        @Override
        public void parser() {

            Document document = spiderContext.getDocument();
            Elements elements = document.getElementsByTag("meta");

            Music163PlayListBO playListBO = new Music163PlayListBO();
            playListBO.setId(this.id);
            playListBO.setUrl(spiderContext.getUrl());
            elements.forEach(element -> {

                String property = element.attr("property");
                switch (property) {
                    case TITLE_KEY:
                        playListBO.setTitle(element.attr("content"));
                        break;
                    case IMAGE_URL_KEY:
                        playListBO.setImageUrl(element.attr("content"));
                        break;
                    case DESCRIPTION_KEY:
                        playListBO.setIntroduction(element.attr("content"));
                        break;
                    default:

                }

            });

            playListBO.setPubDate(getPubDate(document));

            spiderContext.setBusinessData(playListBO);


            System.out.println();
        }

        private Date getPubDate(Document document) {

            Elements elements = document.getElementsByTag("script");
            for (Element element : elements) {
                String scriptType = element.attr("type");
                if (scriptType.equals(SCRIPT_TYPE)) {
                    String dateStr  =JSON.parseObject(element.childNode(0).outerHtml()).getString("pubDate");
                    return DateUtil.parse(dateStr);
                }
            }

            return new Date();
        }

        @Override
        public void business() {

            Music163PlayListBO playListBO = spiderContext.getBusinessData();

        }

        @Override
        public void send() {


        }
    }
}
