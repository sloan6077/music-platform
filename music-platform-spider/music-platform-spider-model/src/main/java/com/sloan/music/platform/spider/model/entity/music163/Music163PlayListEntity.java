package com.sloan.music.platform.spider.model.entity.music163;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Music163PlayListEntity implements Serializable {


    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 歌单url
     */
    private String url;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 发布时间
     */
    private Date pubDate;

    /**
     * 标签
     */
    private String tag;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 收藏数量
     */
    private Integer collectAmount;

    /**
     * 转发数量
     */
    private Integer transmitAmount;

    /**
     * 评论数量
     */
    private Integer commentAmount;

    /**
     * 播放数量
     */
    private Integer playAmount;

}
