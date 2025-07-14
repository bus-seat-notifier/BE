package com.be.bus.mainserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.be.bus.application",
		"com.be.bus.global",
		"com.be.bus.domain",
		"com.be.bus.mainserver"   // HEALTHCHECK API
})
@EnableJpaRepositories(basePackages = "com.be.bus.domain")
@EntityScan(basePackages = "com.be.bus.domain")
public class MainServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainServerApplication.class, args);
	}
}
