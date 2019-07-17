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
public class TaskRecordBO {

    /**
     * 任务记录id
     */
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 任务起始的业务id
     */
    private Long startBizId;

    /**
     * 业务结束的业务id
     */
    private Long endBizId;

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
