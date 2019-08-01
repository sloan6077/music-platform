package com.sloan.music.platform.task.dao.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
@Data
@Accessors(chain = true)
public class TaskExecuteRecordDO {


    private Long id;

    private Long taskId;

    private Date beginTime;

    private Date endTime;

    private Long minBizId;

    private Long maxBizId;

    private String status;
}
