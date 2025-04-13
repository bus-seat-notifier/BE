package com.be.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BusSeatAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusSeatAlarmApplication.class, args);
	}

}
