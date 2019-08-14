package com.sloan.music.platform.spider.service.entity.music163.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/13
 **/
@Data
@Accessors(chain = true)
public class Music163HomePagePlayListMessage implements Serializable {

    private String url;

    private String title;
}
