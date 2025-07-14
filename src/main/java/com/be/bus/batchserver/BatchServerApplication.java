package com.be.bus.batchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {
		"com.be.bus.batchserver",
		"com.be.bus.global",
		"com.be.bus.domain",
		"com.be.bus.batchserver"   // HEALTHCHECK API

})
@EnableJpaRepositories(basePackages = "com.be.bus.domain")
@EntityScan(basePackages = "com.be.bus.domain")
public class BatchServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchServerApplication.class, args);
	}
}
