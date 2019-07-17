package com.sloan.music.platform.task.impl;

import com.sloan.music.platform.task.info.TaskCallable;
import com.sloan.music.platform.task.service.ExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Component
@Slf4j
public class ExecuteServiceImpl implements ExecuteService,DisposableBean {


    @Resource
    private ExecutorService executorService;

    @Override
    public void execute(TaskCallable taskCallable) {

        executorService.submit(taskCallable);
    }

    @Override
    public void shutdown() {

        if (null != executorService) {
            executorService.shutdown();
            executorService = null;
        }
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }
}
