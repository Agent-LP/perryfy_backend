package com.perryfyback.perryfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PerryfyApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(PerryfyApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.allowCredentials(true);
	}
}
