package com.khai.blogapi.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7738023113292649316L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	
}
