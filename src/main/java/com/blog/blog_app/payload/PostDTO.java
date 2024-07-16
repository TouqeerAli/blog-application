package com.blog.blog_app.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.blog_app.model.Category;
import com.blog.blog_app.model.Comment;
import com.blog.blog_app.model.User;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
	
	private String title;
	private String content;
	private String image;
	private Date date;
	
	private CategoryDTO category;
	
	private UserDTO user;
	
	private Set<CommentDTO> comments = new HashSet<>();

}
