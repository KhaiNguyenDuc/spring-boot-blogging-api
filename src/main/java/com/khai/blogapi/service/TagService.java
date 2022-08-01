package com.khai.blogapi.service;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagRequest;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface TagService {

	TagResponse getTagById(Long tagId);

	PageResponse<TagResponse> getTagsByBlog(Long blogId, Integer page, Integer size);

	PageResponse<TagResponse> getAllTags(Integer page, Integer size);

	TagResponse addTag(TagRequest tagRequest);

	ApiResponse deleteTagById(Long tagId);

	ApiResponse deleteAllTag();

	ApiResponse removeTagsByBlog(Long blogId, UserPrincipal userPrincipal);

	TagResponse updateTagById(Long tagId, TagRequest tagRequest);

}
