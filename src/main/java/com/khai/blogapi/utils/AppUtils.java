package com.khai.blogapi.utils;

import com.khai.blogapi.exception.InvalidRequestException;



public class AppUtils {
	
	public static final void validatePageAndSize(Integer page, Integer size) {
		
		if (page < 0) {
			throw new InvalidRequestException("Page number cannot be less than zero.");
		}

		if (size <= 0) {
			throw new InvalidRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstant.MAX_PAGE_SIZE) {
			throw new InvalidRequestException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
		}
	}
}
