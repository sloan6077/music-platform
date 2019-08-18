package com.sloan.music.platform.spider.service.music163;

import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.entity.music163.entity.Music163SongEntity;
import com.sloan.music.platform.spider.service.kafka.KafkaService;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/14
 **/
@Component
@Slf4j
public class SongSpiderEngine {


    @Resource
    private KafkaService kafkaService;

    @KafkaListener(topics = {TopicConstants.MUSIC163_SONG},groupId = "song_spider_engine")
    public void processMessage(ConsumerRecord<String, String> record) {

        String songId = record.value();
    }

    public void test(String songId) {

        SongSpider songSpider = new SongSpider(new SpiderContext<>(),songId);
        songSpider.spider();
    }

    private class SongSpider extends AbstractSpider<String, Music163SongEntity> {

        private static final String SONG_PRE_URL = "https://music.163.com/song?id=";

        private Long id;

        public SongSpider(SpiderContext<String, Music163SongEntity> spiderContext,String songId) {
            super(spiderContext);

            this.id = Long.valueOf(songId);
            Map<String,String> header = new HashMap<>();
            header.put(HeaderKeyConstants.REFERER,"http://music.163.com/");
            header.put(HeaderKeyConstants.HOST,"music.163.com");
            header.put(HeaderKeyConstants.USER_AGENT, UserAgentConstants.CHROME_AGENT);
            header(header);

            url(SONG_PRE_URL + id);
        }

        private static final String TITLE_KEY = "og:title";

        private static final String IMAGE_URL_KEY = "og:image";

        @Override
        public void parser() {

            Document document = spiderContext.getDocument();
            Elements elements = document.getElementsByTag("meta");

            Music163SongEntity music163SongEntity = new Music163SongEntity();
            music163SongEntity.setId(id);
            music163SongEntity.setUrl(spiderContext.getUrl());

            elements.forEach(element -> {

                String property = element.attr("property");
                switch (property) {
                    case TITLE_KEY:
                        music163SongEntity.setTitle(element.attr("content"));
                        break;
                    case IMAGE_URL_KEY:
                        music163SongEntity.setImageUrl(element.attr("content"));
                        break;
                    default:
                }

            });

            music163SongEntity.setPubDate(getPubDate(document));
            assemble(document,music163SongEntity);

            spiderContext.setBusinessData(music163SongEntity);
        }

        private void assemble(Document document, Music163SongEntity music163SongEntity) {

            Elements elements = document.getElementsByClass("s-fc7");

            for (Element element : elements) {
                if (!StringUtils.isEmpty(element.attr("href"))) {

                    String href = element.attr("href");
                    if (href.contains("artist")) {
                        music163SongEntity.setArtistId(Long.valueOf(href.split("=")[1]));
                    } else if (href.contains("album")) {
                        music163SongEntity.setAlbumId(Long.valueOf(href.split("=")[1]));
                    }
                }
            }
        }

        @Override
        public void business() {

            Music163SongEntity music163SongEntity = spiderContext.getBusinessData();

        }

        @Override
        public void send() {

            Document document = spiderContext.getDocument();
            Elements aTags = document.getElementsByTag("a");
            for (Element aElement : aTags) {
                String href = aElement.attr("href");
                if (href.contains("playlist")) {
                    kafkaService.sendMessage(TopicConstants.MUSIC163_PLAYLIST,href.split("id=")[1]);
                }
            }
        }
    }
}
