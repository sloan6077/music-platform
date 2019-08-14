package com.sloan.music.platform.spider.dao;

import com.sloan.music.platform.spider.service.entity.music163.entity.Music163PlayListEntity;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/14
 **/
public interface Music163PlayListMapper {


    /**
     * 插入music163歌单
     * @param entity
     */
    void insert(Music163PlayListEntity entity);

    /**
     * 通过id获取
     * @param id
     * @return
     */
    Music163PlayListEntity getById(Long id);
}
