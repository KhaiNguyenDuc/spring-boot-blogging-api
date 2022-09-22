package com.khai.blogapi.payload;

import java.util.ArrayList;
import java.util.List;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryResponse extends UserDateAudit{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;

	private String description;

	private String title;
	
	private Long userId;
	
	private List<Blog> blogs;
	
	
	public List<Blog> getBlogs() {
		return blogs == null ? null : new ArrayList<>(this.blogs);
	}

	public void setBlogs(List<Blog> blogs) {
		if(blogs == null) {
			this.blogs = null;
		}else {
			this.blogs = blogs;
		}
	}
}
