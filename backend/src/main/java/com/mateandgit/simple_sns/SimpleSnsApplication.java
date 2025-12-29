package com.mateandgit.simple_sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
public class SimpleSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleSnsApplication.class, args);
	}

}
