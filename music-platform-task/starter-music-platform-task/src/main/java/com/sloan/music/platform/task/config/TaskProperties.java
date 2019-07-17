package com.sloan.music.platform.task.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/17
 **/
@ConfigurationProperties("music.platform.task")
@Data
@Accessors(chain = true)
public class TaskProperties {


    private Integer threadNum;
}
