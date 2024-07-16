package com.blog.blog_app.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse {
	
	private List<PostDTO> content;
	private Integer pageNumber;
	private Integer pageSize;
	private long totlaElements;
	private Integer totalPages;
	private boolean isLastPage;

}
