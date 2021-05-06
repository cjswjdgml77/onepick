package com.icia.zboard3.entity;

import java.time.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
	private Integer cno;
	private Integer bno;
	private String writer;
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private LocalDateTime writeTime;
	private String profile;
}
