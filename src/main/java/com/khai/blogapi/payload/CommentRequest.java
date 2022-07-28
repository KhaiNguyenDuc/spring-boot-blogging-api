package com.khai.blogapi.payload;

import java.util.Date;

import lombok.Data;

@Data
public class CommentRequest {

	private String title;

	private String body;

	private Date createDate;

	private Date lastUpdate;

}
