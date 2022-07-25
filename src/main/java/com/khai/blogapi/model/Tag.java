package com.khai.blogapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(mappedBy = "tags")
	private List<Blog> blogs;

	
	@JsonIgnore
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
