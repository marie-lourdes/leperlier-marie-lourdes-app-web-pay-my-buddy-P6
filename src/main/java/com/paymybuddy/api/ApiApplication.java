package com.paymybuddy.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
     @Override
	public  void run (String...args) {
		
	}
}
