package com.wan.tool;

import com.wan.tool.config.IdmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class ToolApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ToolApplication.class, args);
	}

	@Resource
	private IdmConfig idmConfig;

	@Override
	public void run(String... args) throws Exception {

//		log.info("token:{}", idmConfig.idm2Token());
	}
}
