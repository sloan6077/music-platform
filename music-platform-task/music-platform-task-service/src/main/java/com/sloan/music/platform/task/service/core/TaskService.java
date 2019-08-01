package com.sloan.music.platform.task.service.core;

import com.sloan.music.platform.task.service.bo.TaskBO;

import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
public interface TaskService {

    /**
     * 执行任务
     * @param id
     */
    void execute(Long id);

    /**
     * 新增任务
     * @param taskBO
     */
    void addTask(TaskBO taskBO);

    /**
     * 更新任务
     * @param taskBO
     */
    void updateTask(TaskBO taskBO);

    /**
     * 删除任务
     * @param id
     */
    void deleteTask(Long id);

    /**
     * 获取任务
     * @param id
     * @return
     */
    TaskBO getTask(Long id);

    /**
     * 获取任务列表
     * @return
     */
    List<TaskBO> getTaskList();
}
