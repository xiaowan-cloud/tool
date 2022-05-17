package com.wan.tool;

import com.wan.tool.config.IdmConfig;
import com.wan.tool.mapper.idm.IdmMapper;
import com.wan.tool.model.entity.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ToolApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ToolApplication.class, args);
	}

	@Resource
	private IdmConfig idmConfig;

	@Override
	public void run(String... args) throws Exception {
		log.info("=========>>>>tool项目启动完成<<<<=========");
		log.info("token:{}", idmConfig.idm2Token());
	}
}
