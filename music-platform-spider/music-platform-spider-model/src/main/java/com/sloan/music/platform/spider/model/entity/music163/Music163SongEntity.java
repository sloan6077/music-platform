package com.sloan.music.platform.spider.model.entity.music163;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Data
@Accessors(chain = true)
public class Music163SongEntity implements Serializable {

    private Long id;

    private String url;

    private String title;

    private String imageUrl;

    private Date pubDate;

    private Long artistId;

    private Long albumId;

}
