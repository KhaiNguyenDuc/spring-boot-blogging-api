package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.TagResponse;

public interface TagService {

	List<TagResponse> getAllTags();

	TagResponse getTagById(Long tagId);

	List<TagResponse> getTagsByBlog(Long blogId);

}
