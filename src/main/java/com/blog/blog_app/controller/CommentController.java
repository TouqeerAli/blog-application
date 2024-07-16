package com.blog.blog_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog_app.model.Comment;
import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.CommentDTO;
import com.blog.blog_app.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable("postId") Integer postId){
		CommentDTO comment = commentService.createComment(commentDTO, postId);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted.", true, HttpStatus.OK), HttpStatus.OK);
	}
}
