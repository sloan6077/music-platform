package com.sloan.music.platform.spider.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Component
@Slf4j
public class KafkaService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    public KafkaService(KafkaTemplate kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String data) {

        log.info("kafka sendMessage start");

        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {

            log.error("produce error!",e);
        }
    }

    public void stopConsumer(String id) {

        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        listenerContainer.stop();
    }
}
