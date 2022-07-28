package com.khai.blogapi.payload;

import java.util.Date;

import lombok.Data;

@Data
public class BlogRequest {
	
	private String title;

	private String body;
	
	private String description;
	
	private String image;
	
	private Long views;
	
	private Boolean published;
	
	private Date createDate;
	
	private Date lastUpdate;
	
	private Long categoryId;
	
	
}
