package com.wan.tool.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月17日 23:09:46
 */
@Slf4j
@Component
public class ProgramHelper implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("=========>>>>tool项目启动完成<<<<=========");
    }

}
