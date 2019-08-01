package com.sloan.music.platform.task.service.core;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/1
 **/
public interface TaskSplitService {


    /**
     * 分割任务
     * @param taskId
     * @param minBizId
     * @param maxBizId
     * @param taskInterval
     * @param recordId
     */
    void splitTask(Long taskId,Long minBizId,Long maxBizId,Integer taskInterval,Long recordId);
}
