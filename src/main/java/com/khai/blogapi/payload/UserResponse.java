package com.khai.blogapi.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Boolean enabled;
	private Date birthday;
	private String address;
	private String image;

	private List<Blog> blogs;

	private List<Comment> comments;

	private List<Category> categories;

	private List<Role> roles;
	
	public List<Blog> getBlogs() {
		return blogs == null ? null : new ArrayList<>(this.blogs);
	}

	public void setBlogs(List<Blog> blogs) {
		if (blogs == null) {
			this.blogs = null;
		} else {
			this.blogs = blogs;
		}
	}

	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>(this.comments);
	}

	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments = comments;
		}
	}

	public List<Category> getCategories() {
		return categories == null ? null : new ArrayList<>(this.categories);
	}

	public void setCategories(List<Category> categories) {
		if (categories == null) {
			this.categories = null;
		} else {
			this.categories = categories;
		}
	}

	public Boolean isEnabled() {
		return this.enabled;
	}

	public List<Role> getRoles() {
		return roles == null ? null : new ArrayList<>(this.roles);
	}

	public void setRoles(List<Role> roles) {
		if(roles == null) {
			this.roles = null;
		}else {
			this.roles = roles;	
		}
		
	}
	
	
}
