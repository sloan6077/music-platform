package com.sloan.music.platform.task.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Data
@Accessors(chain = true)
public class TaskDTO implements Serializable {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 最小业务id
     */
    private Long minBizId;

    /**
     * 最大业务id
     */
    private Long maxBizId;

    /**
     * 任务所在应用名称
     */
    private String appName;

    /**
     * 任务类名
     */
    private String className;
}
