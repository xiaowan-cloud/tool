package com.wan.tool.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月17日 23:04:50
 */
@Slf4j
//@Component
public class KafkaConsume {

//    @KafkaListener(groupId = "create_finance_event1", topics = {"event_success_data_topic"})
    public void consume(Message<String> message,
                        Consumer consumer,
                        Acknowledgment ack) {
        log.info("消费到kafka消息：{}", message);
    }

}
