package com.blog.blog_app.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.internal.bytebuddy.utility.nullability.MaybeNull;

import com.blog.blog_app.payload.CommentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String content;
	private String image;
	private Date date;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
	

}
