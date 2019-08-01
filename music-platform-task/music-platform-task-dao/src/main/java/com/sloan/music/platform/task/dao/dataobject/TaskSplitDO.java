package com.sloan.music.platform.task.dao.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
@Data
@Accessors(chain = true)
public class TaskSplitDO {

    private Long id;

    private Long taskId;

    private Long beginBizId;

    private Long endBizId;

    private String status;

    private Long recordId;
}
