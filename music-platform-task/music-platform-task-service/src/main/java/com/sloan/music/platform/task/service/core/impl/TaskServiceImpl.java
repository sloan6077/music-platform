package com.sloan.music.platform.task.service.core.impl;

import com.alibaba.dubbo.common.URL;
import com.sloan.music.platform.task.api.dto.TaskDTO;
import com.sloan.music.platform.task.api.service.TaskRemoteService;
import com.sloan.music.platform.task.dao.dataobject.TaskDO;
import com.sloan.music.platform.task.dao.mapper.TaskMapper;
import com.sloan.music.platform.task.service.bo.TaskBO;
import com.sloan.music.platform.task.service.bo.TaskExecuteRecordBO;
import com.sloan.music.platform.task.service.bo.TaskSplitBO;
import com.sloan.music.platform.task.service.core.TaskExecuteRecordService;
import com.sloan.music.platform.task.service.core.TaskService;
import com.sloan.music.platform.task.service.core.TaskSplitService;
import com.sloan.music.platform.task.service.registry.ServiceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private TaskSplitService taskSplitService;

    @Resource
    private TaskExecuteRecordService taskExecuteRecordService;

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

        TaskExecuteRecordBO recordBO
                = new TaskExecuteRecordBO()
                .setTaskId(taskDO.getId())
                .setMinBizId(minBizId)
                .setMaxBizId(maxBizId)
                .setBeginTime(new Date())
                .setStatus("executing");

        taskExecuteRecordService.addTaskExecuteRecord(recordBO);
        List<Long> splitIdList = taskSplitService.splitTask(taskDO.getId(),minBizId,maxBizId,taskInterval,recordBO.getId());

        ExecutorService executorService = Executors.newFixedThreadPool(urls.size());

    }

    private class RemoteExecute implements Runnable {

        private Node node;

        private Long splitId;

        public RemoteExecute(Node node,Long splitId) {

            this.node = node;
            this.splitId = splitId;
        }

        @Override
        public void run() {

            node.setStatus(Status.START);

            TaskSplitBO taskSplitBO = taskSplitService.get(splitId);
            Long taskId = taskSplitBO.getTaskId();
            TaskDO taskDO = taskMapper.getTask(taskId);

            boolean result
                    = node.getTaskRemoteService()
                    .execute(taskDO.getTaskName(),taskSplitBO.getBeginBizId(),taskSplitBO.getEndBizId());

            if (result) {
                node.setStatus(Status.SUCCESS_COMPLETE);
            } else {
                node.setStatus(Status.FAILED_COMPLETE);
            }
        }
    }

    private class Node {

        private TaskRemoteService taskRemoteService;

        private Status status;

        public TaskRemoteService getTaskRemoteService() {
            return taskRemoteService;
        }

        public void setTaskRemoteService(TaskRemoteService taskRemoteService) {
            this.taskRemoteService = taskRemoteService;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

    private enum Status {

        /**
         * 未开始执行
         */
        NOT_START,

        /**
         * 开始执行
         */
        START,

        /**
         * 执行成功
         */
        SUCCESS_COMPLETE,

        /**
         * 执行失败
         */
        FAILED_COMPLETE
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
