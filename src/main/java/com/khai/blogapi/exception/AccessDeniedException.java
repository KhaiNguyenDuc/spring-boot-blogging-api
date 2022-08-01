package com.khai.blogapi.exception;

public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -3553114142305343096L;

	public AccessDeniedException(String message) {
		super(message);
	}

	
}
