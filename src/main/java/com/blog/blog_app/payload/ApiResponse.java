package com.blog.blog_app.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	
	private String message;
	private boolean success;
	private HttpStatus statusCode;
	
	
	

}
