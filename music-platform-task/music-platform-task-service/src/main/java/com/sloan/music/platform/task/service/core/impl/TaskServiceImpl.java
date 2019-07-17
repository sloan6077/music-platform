package com.sloan.music.platform.task.service.core.impl;

import com.sloan.music.platform.task.service.bo.TaskBO;
import com.sloan.music.platform.task.service.core.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
public class TaskServiceImpl implements TaskService {


    @Override
    public void addTask(TaskBO taskBO) {

    }

    @Override
    public void updateTask(TaskBO taskBO) {

    }

    @Override
    public void deleteTask(Long id) {

    }

    @Override
    public TaskBO getTask(Long id) {
        return null;
    }

    @Override
    public List<TaskBO> getTaskList() {
        return null;
    }
}
