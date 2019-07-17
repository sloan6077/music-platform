package com.sloan.music.platform.task.impl;

import com.sloan.music.platform.task.annotation.Task;
import com.sloan.music.platform.task.api.dto.TaskDTO;
import com.sloan.music.platform.task.service.TaskBeanCollect;
import com.sloan.music.platform.task.util.ApplicationContextUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/18
 **/
@Component
public class TaskBeanCollectImpl implements TaskBeanCollect, InitializingBean {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void collect() {

        Map<String,Object> beanMap = ApplicationContextUtil.getMapbeanwithAnnotion(Task.class);
        if (!CollectionUtils.isEmpty(beanMap)) {

            List<TaskDTO> taskDTOS = beanMap.keySet().stream().map(key -> {

                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setAppName(appName);
                taskDTO.setClassName(beanMap.get(key).getClass().getName());
                taskDTO.setTaskName(key);

                return taskDTO;

            }).collect(Collectors.toList());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        collect();
    }
}
