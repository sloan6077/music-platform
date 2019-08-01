package com.sloan.music.platform.task.service.core.impl;

import com.alibaba.dubbo.common.URL;
import com.sloan.music.platform.task.api.dto.TaskDTO;
import com.sloan.music.platform.task.api.service.TaskRemoteService;
import com.sloan.music.platform.task.dao.dataobject.TaskDO;
import com.sloan.music.platform.task.dao.mapper.TaskMapper;
import com.sloan.music.platform.task.service.bo.TaskBO;
import com.sloan.music.platform.task.service.core.TaskService;
import com.sloan.music.platform.task.service.registry.ServiceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Resource
    private ServiceFilter serviceFilter;

    @Resource
    private TaskMapper taskMapper;

    @Override
    public void execute(Long id) {

        TaskDO taskDO = taskMapper.getTask(id);

        String taskName = taskDO.getTaskName();
        Integer taskInterval = taskDO.getTaskInterval();

        List<URL> urls = serviceFilter.filterAppName(taskDO.getAppName());
        TaskRemoteService taskRemoteService = serviceFilter.getByURL(TaskRemoteService.class,urls.get(0));
        TaskDTO taskDTO = taskRemoteService.getTaskInfo(taskName);

        Long minBizId = taskDTO.getMinBizId();
        Long maxBizId = taskDTO.getMaxBizId();



    }

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
