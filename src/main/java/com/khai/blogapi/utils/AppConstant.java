package com.khai.blogapi.utils;

public class AppConstant {

	public static final String CATEGORY_NOT_FOUND = "Not found category with id: ";
	public static final String BLOG_NOT_FOUND = "Not found blog with id: ";
	public static final String TAG_NOT_FOUND = "Not found tag with id: ";
	public static final String COMMENT_NOT_FOUND = "Not found comment with id: ";
	public static final String USER_NOT_FOUND = "Not found user has : ";
	public static final String ROLE_NOT_FOUND = "Not found role with name : ";
	public static final String ROLE_NOT_FOUND_WITH_USER = "Not found role with this user has name : ";
	
	public static final String CATEGORY_EXIST = "This category already exist in database";
	public static final String BLOG_EXIST = "This blog already exist in database";
	public static final String TAG_EXIST = "This tag already exist in database";
	public static final String COMMENT_EXIST = "This comment already exist in database with id: ";
	public static final String TAG_ID_EXIST = "This id is already exist in database (recomment not to pass id in json )";
	public static final String USER_EXIST = "This user already exist in database";
	public static final String ROLE_WITH_USER_EXIST = "This role already set to this user";
	
	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_PAGE_SIZE = "1"; 
	public static final Integer MAX_PAGE_SIZE = 10;
	
	public static final String CATEGORY_DELETE_MESSAGE = "Sucessfully delete this category";
	public static final String COMMENT_DELETE_MESSAGE = "Sucessfully delete this comment";
	public static final String TAG_DELETE_MESSAGE = "Sucessfully delete this tag";
	public static final String BLOG_DELETE_MESSAGE = "Sucessfully delete this blog";
	public static final String USER_DELETE_MESSAGE = "Sucessfully delete this user";

	public static final String USER_UPDATE_DENY = "You don't have permission to update this user";
	public static final String USER_DELETE_DENY = "You don't have permission to delete this user";
	
	public static final String COMMENT_UPDATE_DENY = "You don't have permission to update this comment";
	public static final String COMMENT_DELETE_DENY = "You don't have permission to delete this comment";
	
	public static final String BLOG_UPDATE_DENY = "You don't have permission to update this blog";
	public static final String BLOG_DELETE_DENY = "You don't have permission to delete this blog";
	
	public static final String CATEGORY_UPDATE_DENY = "You don't have permission to update this category";
	public static final String CATEGORY_DELETE_DENY = "You don't have permission to delete this category";
	
	public static final String TAG_REMOVE_DENY = "You don't have permission to remove tags from this blog";

	public static final String GIVE_ADMIN = "Give admin sucessfully";
	public static final String TAKE_ADMIN = "Take admin sucessfully";
}
