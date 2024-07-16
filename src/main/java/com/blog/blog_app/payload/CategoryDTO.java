package com.blog.blog_app.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	
	private Integer id;
	

	@NotBlank
	@Size(min = 6, max=10)
	private String title;
	
	@NotBlank
	@Size(min = 10, max=100)
	private String description;

}
