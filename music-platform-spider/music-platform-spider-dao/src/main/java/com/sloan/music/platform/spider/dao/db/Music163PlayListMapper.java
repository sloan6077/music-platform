package com.sloan.music.platform.spider.dao.db;


import com.sloan.music.platform.spider.model.entity.music163.Music163PlayListEntity;

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
