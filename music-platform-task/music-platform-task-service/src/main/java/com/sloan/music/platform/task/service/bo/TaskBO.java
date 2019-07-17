package com.sloan.music.platform.task.service.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@Data
@Accessors(chain = true)
public class TaskBO {

    /**
     * 任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 任务执行间隔
     */
    private Integer taskInterval;

    /**
     * 任务所在应用名称
     */
    private String appName;

    /**
     * 任务类名
     */
    private String className;

    /**
     * 删除时间
     */
    private Date deletedAt;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
}
