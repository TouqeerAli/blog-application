package com.blog.blog_app.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_app.model.Category;
import com.blog.blog_app.model.Post;
import com.blog.blog_app.model.User;
import com.blog.blog_app.payload.PostDTO;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user, Pageable pageable);
	List<Post> findByCategory(Category category, Pageable pageable);
	List<Post> findByTitleContaining(String title);
	
}
