package com.sloan.music.platform.spider.controller;

import com.alibaba.fastjson.JSON;
import com.sloan.music.platform.spider.service.entity.music163.bo.Music163SongBO;
import com.sloan.music.platform.spider.service.kafka.KafkaService;
import com.sloan.music.platform.spider.service.kafka.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProduceController {


    @Resource
    private KafkaService kafkaService;

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public void produce(@RequestBody Music163SongBO music163SongBO) {


        kafkaService.sendMessage(TopicConstants.MUSIC163_SONG, JSON.toJSONString(music163SongBO));
    }
}
