package com.sloan.music.platform.spider.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Component
@Slf4j
public class KafkaService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaService(KafkaTemplate kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String data) {

        log.info("kafka sendMessage start");

        kafkaTemplate.send(topic, data);
    }

    @KafkaListener(topics = TopicConstants.MUSIC163_PLAYLIST, groupId = "myGroup")
    public void processMessage(ConsumerRecord<String, String> record) {

        log.info("processMessage, topic = {}, msg = {}", record.topic(), record.value());
    }
}
