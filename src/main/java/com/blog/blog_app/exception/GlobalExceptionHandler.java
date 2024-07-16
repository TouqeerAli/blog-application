package com.blog.blog_app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.blog_app.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		
		ApiResponse apiResponse = new ApiResponse(message,false,HttpStatus.NOT_FOUND);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String,String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String message = error.getDefaultMessage();
			String fieldErrorName = ex.getFieldError().getField();
			response.put(fieldErrorName,message);
		});
		
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}

}
