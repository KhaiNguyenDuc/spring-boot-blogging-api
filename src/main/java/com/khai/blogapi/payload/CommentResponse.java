package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class CommentResponse {

	private Long id;
	
	private String title;
	
	private String body;
		
	private Long blogId;
	
	private Long userId;
}
