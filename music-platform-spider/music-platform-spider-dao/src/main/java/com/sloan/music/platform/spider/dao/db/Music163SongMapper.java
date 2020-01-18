package com.sloan.music.platform.spider.dao.db;


import com.sloan.music.platform.spider.model.entity.music163.Music163SongEntity;

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
