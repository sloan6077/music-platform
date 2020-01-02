package com.sloan.music.platform.spider.service.kafka;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
public final class TopicConstants {


    public static final class Music163Song {

        public static final String TOPIC_SONG = "music163_song";

        public static final String GROUP_SONG_SPIDER = "song_spider_engine";

        public static final String GROUP_SONG_LYRIC_SPIDER = "song_lyric_spider_engine";
    }

    public static final class Music163PlayList {

        public static final String TOPIC_PLAYLIST = "music163_playlist";

        public static final String GROUP_PLAYLIST_SPIDER = "play_list_spider_engine";
    }

}
