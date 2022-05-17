package com.wan.tool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author：梁亚泽
 * @title：默认描述
 * @date：11:27 2020/7/1
 * @修改人：
 * @修改时间：11:27 2020/7/1
 * @修改描述：默认描述
 */
@Component
@Configuration
public class TestJob {
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Scheduled(fixedDelay =5000)
    public void reportCurrentTime() {
        System.out.println("当前时间 -->"+dateFormat.format(new Date()));
        Random random = new Random();
        int i = random.nextInt(99999);
    }
}
