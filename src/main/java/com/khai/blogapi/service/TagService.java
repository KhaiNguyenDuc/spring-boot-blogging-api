package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagResponse;

public interface TagService {

	TagResponse getTagById(Long tagId);

	PageResponse<TagResponse> getTagsByBlog(Long blogId, Integer page, Integer size);

	PageResponse<TagResponse> getAllTags(Integer page, Integer size);

}
