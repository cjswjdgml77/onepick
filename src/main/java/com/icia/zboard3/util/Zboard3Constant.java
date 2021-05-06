package com.icia.zboard3.util;

public interface Zboard3Constant {
	public static final String ServicePath = "serviceCenter/serviceCenterMain.jsp";
	
	public static final int BOARD_PER_PAGE = 10;
	public static final int PAGE_PER_PAGE = 5;
	
	public static final String AttachmentPath= "http://localhost:8081/attachment/";
	public static final String AttachmentFolder= "c:/upload/attachment";
	
	public static final String TempFolder = "c:/upload/temp";
	public static final String TempPath = "http://localhost:8081/temp/";

	public static final String ImageFolder = "c:/upload/image";
	
	public static final String CK_PATTERN = "src=\"http://localhost:8081/temp/";
	public static final String CK_REPLACE = "src=\"http://localhost:8081/image/";
	
	public static final String ProfileFolder = "c:/upload/profile";
	public static final String ProfilePath = "http://localhost:8081/profile/";
}
