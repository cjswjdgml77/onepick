package com.icia.zboard3.exception;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqBoardJob {

	public static class BoardNotFoundException extends RuntimeException{

	}

	public static class CommentNotFoundException extends RuntimeException{

	}

	public static class IllegalAccessException extends RuntimeException {

	}

	public static class AttachmentNotFoundException extends RuntimeException {

	}

	public static class MemoNotFoundException extends RuntimeException{

	}
	
}
