package com.blog.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_app.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	

}
