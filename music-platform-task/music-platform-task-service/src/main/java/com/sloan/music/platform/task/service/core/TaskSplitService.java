package com.sloan.music.platform.task.service.core;

import com.sloan.music.platform.task.service.bo.TaskSplitBO;

import java.util.List;

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
     * @return
     */
    List<Long> splitTask(Long taskId, Long minBizId, Long maxBizId, Integer taskInterval, Long recordId);


    /**
     * 根据id获取
     * @param id
     * @return
     */
    TaskSplitBO get(Long id);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(Long id,String status);
}
