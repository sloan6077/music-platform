package com.sloan.music.platform.task.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sloan.music.platform.task.api.dto.TaskDTO;
import com.sloan.music.platform.task.api.service.TaskRemoteService;
import com.sloan.music.platform.task.info.TaskCallable;
import com.sloan.music.platform.task.service.TaskBean;
import com.sloan.music.platform.task.util.ApplicationContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Service
@Slf4j
public class TaskRemoteServiceImpl implements TaskRemoteService {

    @Resource
    @Qualifier("taskExecutor")
    private ExecutorService executorService;

    @Override
    @SneakyThrows
    public Boolean execute(String taskName, Long startId, Long endId) {

        TaskBean taskBean = ApplicationContextUtil.getBean(taskName,TaskBean.class);
        List<Long> idList = taskBean.getIdList(startId,endId);
        if (CollectionUtils.isEmpty(idList)) {

            return true;
        }

        CountDownLatch countDownLatch = new CountDownLatch(idList.size());

        idList.forEach(bizId -> executorService.submit(new TaskCallable(taskName,bizId,countDownLatch)));

        countDownLatch.await();

        return true;
    }

    @Override
    public TaskDTO getTaskInfo(String taskName) {
        return null;
    }
}
