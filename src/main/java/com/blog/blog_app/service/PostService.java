package com.blog.blog_app.service;

import java.util.List;

import com.blog.blog_app.model.Post;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;

public interface PostService {

	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryid);
	PostDTO updatePost(PostDTO postDTO, Integer id);
	void deletePost(Integer id);
	PostDTO getPost(Integer id);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);
	PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);
	PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	List<PostDTO> searchPostByTitle(String title);
}
