package com.sloan.music.platform.spider.service.bo.music163;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Data
@Accessors(chain = true)
public class Music163SongBO {


    private Long id;

    private String name;
}
