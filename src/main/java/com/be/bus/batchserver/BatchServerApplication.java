package com.be.bus.batchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.be.bus.batchserver", "com.be.bus.global", "com.be.bus.domain"
})
public class BatchServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchServerApplication.class, args);
	}
}
