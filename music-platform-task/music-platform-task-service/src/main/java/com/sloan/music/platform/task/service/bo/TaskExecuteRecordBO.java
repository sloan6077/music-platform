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
public class TaskExecuteRecordBO {

    private Long id;

    private Long taskId;

    private Date beginTime;

    private Date endTime;

    private Long minBizId;

    private Long maxBizId;

    private String status;
}
