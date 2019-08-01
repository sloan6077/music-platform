package com.sloan.music.platform.task.service.core;

import com.sloan.music.platform.task.service.bo.TaskExecuteRecordBO;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/1
 **/
public interface TaskExecuteRecordService {


    /**
     * 新增任务执行记录
     * @param taskExecuteRecordBO
     */
    void addTaskExecuteRecord(TaskExecuteRecordBO taskExecuteRecordBO);
}
