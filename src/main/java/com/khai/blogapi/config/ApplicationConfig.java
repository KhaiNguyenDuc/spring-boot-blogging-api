package com.khai.blogapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
