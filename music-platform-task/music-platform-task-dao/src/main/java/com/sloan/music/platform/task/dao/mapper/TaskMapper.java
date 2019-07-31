package com.sloan.music.platform.task.dao.mapper;

import com.sloan.music.platform.task.dao.dataobject.TaskDO;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/20
 **/
public interface TaskMapper {


    /**
     * 新增任务
     * @param taskDO
     */
    void insert(TaskDO taskDO);
}
