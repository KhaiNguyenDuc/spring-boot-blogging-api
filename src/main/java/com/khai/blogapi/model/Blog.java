package com.khai.blogapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "views")
	private Long views;
	
	@Column(name = "published")
	private Boolean published;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "last_update")
	private Date lastUpdate;
	
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Comment> comments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;
	
	@ManyToMany
	@JoinTable(
			name = "blog_tag",
			joinColumns = @JoinColumn(name = "blog_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
			)
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
