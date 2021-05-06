package com.icia.zboard3.dto;

import java.util.*;

import com.icia.zboard3.entity.*;

import lombok.*;

public class CommentDto {
	@Data
	public static class Write {
		private Integer bno;
		private String content;
	}

	@Data
	public static class Read {
		private Integer cno;
		private Integer bno;
		private String writer;
		private Boolean isWriter;
		private String content;
		private String writeTimeString;
		private String profile;
	}
	@Data
	@AllArgsConstructor
	public static class Rest{
		private List<Comment> comments; 
		private Integer nextCommentPage;
	}
	
}
