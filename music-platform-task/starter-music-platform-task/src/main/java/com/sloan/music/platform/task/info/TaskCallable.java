package com.sloan.music.platform.task.info;

import com.sloan.music.platform.task.service.TaskBean;
import com.sloan.music.platform.task.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Slf4j
public class TaskCallable implements Runnable {

    private CountDownLatch countDownLatch;

    private String taskName;

    private Long bizId;

    public TaskCallable(String taskName, Long bizId, CountDownLatch countDownLatch) {

        this.countDownLatch = countDownLatch;
        this.bizId = bizId;
        this.taskName = taskName;
    }

    @Override
    public void run() {

        try {

            ApplicationContextUtil.getBean(taskName, TaskBean.class).process(bizId);

        } catch (Throwable e) {

            log.error("execute taskName : {} error!",taskName,e);

        } finally {

            if (null != countDownLatch) {
                countDownLatch.countDown();
            }
        }
    }
}
