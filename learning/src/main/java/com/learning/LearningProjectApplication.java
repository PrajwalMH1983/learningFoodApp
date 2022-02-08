package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class LearningProjectApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext1 = SpringApplication.run(LearningProjectApplication.class,
				args);
	}
}