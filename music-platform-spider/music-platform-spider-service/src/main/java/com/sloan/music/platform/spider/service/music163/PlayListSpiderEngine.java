package com.sloan.music.platform.spider.service.music163;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.sloan.music.platform.spider.dao.Music163PlayListMapper;
import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.entity.music163.entity.Music163PlayListEntity;
import com.sloan.music.platform.spider.service.kafka.KafkaService;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import com.sloan.music.platform.spider.service.util.DateUtil;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/13
 **/
@Component
@Slf4j
public class PlayListSpiderEngine {


    @Resource
    private KafkaService kafkaService;

    @Resource
    private Music163PlayListMapper music163PlayListMapper;

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

    private class PlayListSpider extends AbstractSpider<String, Music163PlayListEntity> {

        private static final String PLAY_LIST_PRE_URL = "https://music.163.com/playlist?id=";

        private Long id;

        PlayListSpider(SpiderContext<String, Music163PlayListEntity> spiderContext, String playListId) {

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

            Music163PlayListEntity playListEntity = new Music163PlayListEntity();
            playListEntity.setId(this.id);
            playListEntity.setUrl(spiderContext.getUrl());
            elements.forEach(element -> {

                String property = element.attr("property");
                switch (property) {
                    case TITLE_KEY:
                        playListEntity.setTitle(element.attr("content"));
                        break;
                    case IMAGE_URL_KEY:
                        playListEntity.setImageUrl(element.attr("content"));
                        break;
                    case DESCRIPTION_KEY:
                        playListEntity.setIntroduction(element.attr("content"));
                        break;
                    default:

                }

            });

            playListEntity.setPubDate(getPubDate(document));
            playListEntity.setTag(getTag(document));

            assemble(document,playListEntity);

            spiderContext.setBusinessData(playListEntity);

            log.info("playlist:{}",playListEntity);
        }

        private void assemble(Document document, Music163PlayListEntity playListEntity) {

            List<Node> nodes = document.getElementById("content-operation").childNodes();
            for (Node node : nodes) {
                if (node instanceof Element) {
                    Element e = (Element)node;
                    String attrValue = e.attr("data-res-action");
                    switch (attrValue) {

                        case "fav":
                            playListEntity.setCollectAmount(Integer.valueOf(e.attr("data-count")));
                            break;
                        case "share":
                            playListEntity.setTransmitAmount(Integer.valueOf(e.attr("data-count")));
                            break;
                        default:

                    }
                }
            }

            String commentCntStr = document.getElementById("cnt_comment_count").childNodes().get(0).outerHtml();
            if ("评论".equals(commentCntStr)) {
                playListEntity.setCommentAmount(0);
            } else {
                Integer commentCnt = Integer.valueOf(commentCntStr);
                playListEntity.setCommentAmount(commentCnt);
            }
            Integer playAmount = Integer.valueOf(document.getElementById("play-count").childNodes().get(0).outerHtml());
            playListEntity.setPlayAmount(playAmount);
        }

        private String getTag(Document document) {

            Elements elements = document.getElementsByClass("u-tag");
            List<String> tags = elements.stream()
                    .map(element -> element.childNode(0).childNode(0).outerHtml()).collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(tags);
        }

        @Override
        public void business() {

            Music163PlayListEntity playListEntity = spiderContext.getBusinessData();
            music163PlayListMapper.insert(playListEntity);
        }

        @Override
        public void send() {

            Document document = spiderContext.getDocument();
            document.select("ul[class=f-hide] a").forEach(element -> {
                String songId = element.attr("href").split("id=")[1];
                kafkaService.sendMessage(TopicConstants.MUSIC163_SONG,songId);
            });
        }
    }
}
