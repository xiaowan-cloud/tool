package com.wan.tool.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月17日 22:46:31
 */
@Slf4j
@Component
public class KafkaProduce {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void produceMeg(String topic, String msg) {
        log.info("开始发送kafka消息至{}，发送信息为：{}", topic, msg);
        kafkaTemplate.send(topic, msg);
        log.info(">>>>>>>>>>kafka消息发送完毕<<<<<<<<<<<<");
    }

}

