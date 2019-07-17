package com.sloan.music.platform.task.service;


import com.sloan.music.platform.task.info.TaskCallable;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/4/26
 **/
public interface ExecuteService {


    /**
     * 执行
     * @param taskCallable
     */
    void execute(TaskCallable taskCallable);

    /**
     * 关闭线程池
     */
    void shutdown();
}
