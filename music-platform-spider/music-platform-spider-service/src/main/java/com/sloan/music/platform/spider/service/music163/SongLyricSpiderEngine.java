package com.sloan.music.platform.spider.service.music163;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sloan.music.platform.spider.service.core.AbstractSpider;
import com.sloan.music.platform.spider.service.core.SpiderContext;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import com.sloan.music.platform.spider.service.util.HeaderKeyConstants;
import com.sloan.music.platform.spider.service.util.UserAgentConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jsoup.nodes.Document;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/20
 **/
@Component
@Slf4j
public class SongLyricSpiderEngine {


    @KafkaListener(topics = {TopicConstants.Music163Song.TOPIC_SONG},groupId = TopicConstants.Music163Song.GROUP_SONG_LYRIC_SPIDER)
    public void processMessage(ConsumerRecord<String, String> record) {


    }

    public void test(String songId) {

        SongLyricSpider songLyricSpider = new SongLyricSpider(new SpiderContext<>(),songId);
        songLyricSpider.spider();
    }

    private class SongLyricSpider extends AbstractSpider<String, Void> {


        private static final String SONG_LYRIC_URL = "http://music.163.com/api/song/lyric?id=%s&lv=1&kv=1&tv=-1";

        private Long id;

        public SongLyricSpider(SpiderContext<String, Void> spiderContext,String songId) {
            super(spiderContext);

            this.id = Long.valueOf(songId);

            Map<String,String> header = new HashMap<>();
            header.put(HeaderKeyConstants.REFERER,"http://music.163.com/");
            header.put(HeaderKeyConstants.HOST,"music.163.com");
            header.put(HeaderKeyConstants.USER_AGENT, UserAgentConstants.CHROME_AGENT);
            header(header);

            url(String.format(SONG_LYRIC_URL,songId));
        }

        @Override
        public void parser() {

            Document document = spiderContext.getDocument();

            String body = document.body().text();
            JSONObject obj = JSON.parseObject(body, JSONObject.class);
            String lyric= obj.getJSONObject("lrc").getString("lyric");
            String[] arr = lyric.split("\n");

            Arrays.asList(arr).forEach(str -> {

                str = str.trim();
                int start = str.indexOf("[");
                int end = str.indexOf("]");
                if (!str.substring(start,end).contains("-")) {

                    str = str.substring(end + 1);
                    if (StringUtils.hasText(str)) {
                        System.out.println(str);
                    }
                }
            });

        }

        @Override
        public void business() {

            //存储到es

        }
    }
}
