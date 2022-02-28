package com.spring.week02magazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Week02magazineApplication {

	public static void main(String[] args) {
		SpringApplication.run(Week02magazineApplication.class, args);
	}

}
