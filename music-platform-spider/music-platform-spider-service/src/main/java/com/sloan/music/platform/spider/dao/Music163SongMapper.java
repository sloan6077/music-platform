package com.sloan.music.platform.spider.dao;

import com.sloan.music.platform.spider.service.entity.music163.entity.Music163SongEntity;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/18
 **/
public interface Music163SongMapper {


    /**
     * 新增歌曲
     * @param music163SongEntity
     */
    void insert(Music163SongEntity music163SongEntity);
}
