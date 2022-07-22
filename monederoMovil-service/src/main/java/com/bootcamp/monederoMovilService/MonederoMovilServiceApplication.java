package com.bootcamp.monederoMovilService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MonederoMovilServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonederoMovilServiceApplication.class, args);
	}

}
