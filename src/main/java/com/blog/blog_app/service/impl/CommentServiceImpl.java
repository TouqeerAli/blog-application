package com.blog.blog_app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.model.Comment;
import com.blog.blog_app.model.Post;
import com.blog.blog_app.payload.CommentDTO;
import com.blog.blog_app.repository.CommentRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		 Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " postId", postId));
		 Comment comment = this.mapper.map(commentDTO, Comment.class);
		 comment.setPost(post);
		 Comment savedComment = commentRepo.save(comment);
		 
		return this.mapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", " commentId", commentId));
		commentRepo.delete(this.mapper.map(comment, Comment.class));
	}

}
