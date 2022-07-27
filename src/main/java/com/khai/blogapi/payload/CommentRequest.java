package com.khai.blogapi.payload;

import java.util.Date;

import com.khai.blogapi.model.Blog;

import lombok.Data;

@Data
public class CommentRequest {
	private Long id;

	private String title;

	private String body;

	private Date createDate;

	private Date lastUpdate;

	private Blog blog;
}
