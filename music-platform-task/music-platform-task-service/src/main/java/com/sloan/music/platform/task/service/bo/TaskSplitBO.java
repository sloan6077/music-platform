package com.sloan.music.platform.task.service.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/1
 **/
@Data
@Accessors(chain = true)
public class TaskSplitBO {


    private Long id;

    private Long taskId;

    private Long beginBizId;

    private Long endBizId;

    private String status;

    private Long recordId;
}
