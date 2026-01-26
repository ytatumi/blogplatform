package com.example.blogplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.blogplatform")
public class BlogplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogplatformApplication.class, args);
	}

}
