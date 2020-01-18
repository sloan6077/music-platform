package com.sloan.music.platform.spider.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "music",type = "_doc")
public class MusicSongEntity {

    @Id
    private String id;

    /**
     * 歌曲名称
     */
    private String songName;

    /**
     * 歌曲id
     */
    private String songId;

    /**
     * 歌手id
     */
    private String artistId;

    /**
     * 歌手名称
     */
    private String artistName;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 评论数量
     */
    private Integer commentCnt;

    /**
     * 来源
     */
    private String source;
}
