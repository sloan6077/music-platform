package com.sloan.music.platform.spider.service.entity.music163.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Data
@Accessors(chain = true)
public class Music163SongBO implements Serializable {

    private Long id;

    private String name;
}
