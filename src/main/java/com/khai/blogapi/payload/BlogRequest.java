package com.khai.blogapi.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Tag;

import lombok.Data;

@Data
public class BlogRequest {
	
	private Long id;
	
	private String title;

	private String body;
	
	private String description;
	
	private String image;
	
	private Long views;
	
	private Boolean published;
	
	private Date createDate;
	
	private Date lastUpdate;
	
	private List<Comment> comments;
	
	private Category category;
	
	private List<Tag> tags;

	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>(this.comments);
	}

	public void setComments(List<Comment> comments) {
		if(comments == null) {
			this.comments = null;
		}else {
			this.comments = comments;
		}
		
	}
	
	public List<Tag> getTags() {
		return tags == null ? null : new ArrayList<>(this.tags);
	}

	public void setTags(List<Tag> tags) {
		if(tags == null) {
			this.tags = null;
		}else {
			this.tags = tags;
		}
	}
}
