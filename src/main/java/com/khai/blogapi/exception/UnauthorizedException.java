package com.khai.blogapi.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1730694003032222533L;

	public UnauthorizedException(String message) {
		super(message);
	}

	
}
