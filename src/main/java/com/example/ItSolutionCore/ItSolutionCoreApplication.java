package com.example.ItSolutionCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ItSolutionCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItSolutionCoreApplication.class, args);
	}

}
