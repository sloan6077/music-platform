package com.sloan.music.platform.task.api.service;

import com.sloan.music.platform.task.api.dto.TaskDTO;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
public interface TaskRemoteService {


    /**
     * 执行任务
     * @param taskName
     * @param startId
     * @param endId
     */
    Boolean execute(String taskName, Long startId, Long endId);

    /**
     * 获取任务信息
     * @param taskName
     * @return
     */
    TaskDTO getTaskInfo(String taskName);
}
