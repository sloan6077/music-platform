package com.sloan.music.platform.spider.service.entity.music163.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/13
 **/
@Data
@Accessors(chain = true)
public class Music163PlayListBO implements Serializable {


    private Long id;

    private String title;

    private String url;

    private String imageUrl;

    private Date pubDate;

    private String tag;

    private String introduction;

    private Integer collectAmount;

    private Integer transmitAmount;

    private Integer commentAmount;

    private Integer playAmount;

}
