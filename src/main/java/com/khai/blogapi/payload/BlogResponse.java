package com.khai.blogapi.payload;

import java.util.ArrayList;
import java.util.List;

import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlogResponse extends UserDateAudit{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String body;

	private String description;

	private String image;

	private Long views;

	private Boolean published;

	private List<Comment> comments;

	private Long categoryId;
	
	private Long userId;

	private List<Tag> tags;


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

	public List<Tag> getTags() {
		return tags == null ? null : new ArrayList<>(this.tags);
	}

	public void setTags(List<Tag> tags) {
		if (tags == null) {
			this.tags = null;
		} else {
			this.tags = tags;
		}
	}
}
