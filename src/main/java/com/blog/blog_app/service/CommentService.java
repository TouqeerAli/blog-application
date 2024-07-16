package com.blog.blog_app.service;

import com.blog.blog_app.payload.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO, Integer postId);
	void deleteComment(Integer commentId);

}
