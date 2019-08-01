package com.sloan.music.platform.task.dao.mapper;

import com.sloan.music.platform.task.dao.dataobject.TaskDO;

import java.util.List;

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

    /**
     * 更新任务
     * @param taskDO
     */
    void update(TaskDO taskDO);

    /**
     * 删除任务
     * @param id
     */
    void delete(Long id);

    /**
     * 获取任务
     * @param id
     * @return
     */
    TaskDO getTask(Long id);

    /**
     * 获取所有任务
     * @return
     */
    List<TaskDO> getTaskList();
}
