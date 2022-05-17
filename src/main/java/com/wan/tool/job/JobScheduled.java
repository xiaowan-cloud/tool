package com.wan.tool.job;

import cn.hutool.core.date.DateUtil;
import com.wan.tool.kafka.KafkaProduce;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月17日 22:59:43
 */
@Component
public class JobScheduled {

    @Resource
    private KafkaProduce kafkaProducer;

    @Scheduled(fixedDelay = 1000)
    public void fixedDelayJob() {
        kafkaProducer.produceMeg("event_success_data_topic", DateUtil.now()+"发送kafka消息");
    }

}
