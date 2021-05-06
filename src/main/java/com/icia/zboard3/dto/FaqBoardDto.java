package com.icia.zboard3.dto;

import java.util.*;

import javax.validation.constraints.NotNull;

import com.icia.zboard3.entity.*;
import com.sun.istack.*;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class FaqBoardDto {
	@Data
	public static class ListView{
		private Integer bno;
		private String title;
		private String writer;
		private String writeTimeString;
		private Integer attachmentCnt;
		private Integer readCnt;
		private Integer commentCnt;
	}
	@Data
	public static class Write {
		@NotNull
		private String title;
		private String content;
	}
	@Data
	public static class Read {
		private Integer bno;
		private String title;
		private String content;
		private String writer;
		private String writeTimeString;
		private Integer readCnt;
		private Integer attachmentCnt;
		private Integer goodCnt;
		private Integer badCnt;
		private Boolean isWriter;
		private List<Attachment> attachments;
		private List<CommentDto.Read> comments;
		private Integer nextCommentPage;
	}
	@Data
	public static class Update {
		@NotNull
		private Integer bno;
		@NotNull
		private String title;
		private String content;
	}
}
