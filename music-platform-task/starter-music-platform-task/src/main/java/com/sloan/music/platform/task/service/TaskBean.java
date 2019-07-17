package com.sloan.music.platform.task.service;

import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
public interface TaskBean {

    /**
     * 业务逻辑方法
     * @param bizId
     */
    void process(Long bizId);

    /**
     * 获取id列表
     * @param startId
     * @param endId
     * @return
     */
    List<Long> getIdList(Long startId,Long endId);


    /**
     * 获取最小的id
     * @return
     */
    Long getMinId();

    /**
     * 获取最大的id
     * @return
     */
    Long getMaxId();
}
