package com.khai.blogapi.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private Boolean success;
	private String message;
	private HttpStatus http;
	
	
}
