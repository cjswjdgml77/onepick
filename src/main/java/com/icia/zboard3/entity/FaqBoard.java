package com.icia.zboard3.entity;

import java.time.*;

import lombok.*;
import lombok.experimental.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class FaqBoard {
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime writeTime;
	private Integer readCnt;
	private Integer attachmentCnt; 
	private Integer commentCnt;
	private Integer deleteCommentCnt;
	private Integer goodCnt;
	private Integer badCnt;
	
}
