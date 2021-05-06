<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<sec:authorize access="hasRole('ROLE_USER')">
	<script src="/zboard3/script/wsocket.js"></script>
</sec:authorize>
<script>
$(function() {
	$("#btnSend").on("click", function() {
		location.href = "/zboard3/memo/send";
	});
	$("#btnReceive").on("click", function() {
		location.href = "/zboard3/memo/receive";
	});	
})
</script>
<style>
#mailBox{
	margin-top:100px;
	margin-left:auto;
	margin-right:auto;
	width:300px;
	border: 1px solid black;
	text-align:center;
}
#mailBox td{
	height:50px;
}
#top_title{
	text-align:center;
}
.content1{
	min-height:100px;
}
.front-table{
	width:70px;
}
</style>
</head>
<body>
	<table id="mailBox">
		<tr><th colspan="2" id="top_title" class="font-talbe">쪽지함</th></tr>
		<tr><td class="font-talbe">보낸이</td><td> ${memo.sender }</td></tr>
		<tr><td class="font-talbe">시간</td><td>${memo.writeTimeString}</td></tr>
		<tr><td class="font-talbe">제목</td><td>${memo.title }</td></tr> 
		<tr><td class="font-talbe"><div class="content1">내용</div></td><td><div class="content1">${memo.content }</div></td></tr>
		<tr><td class="font-talbe"><button id="btnSend">보낸 메모 페이지로</button></td><td><button id="btnReceive">받은 메모 페이지로</button></td></tr>
	</table>
</body>
</html>