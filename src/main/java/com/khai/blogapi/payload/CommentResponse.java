package com.khai.blogapi.payload;

import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentResponse extends UserDateAudit{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	
	private String body;
	
	private Long blogId;
	
	private Long userId;
}
