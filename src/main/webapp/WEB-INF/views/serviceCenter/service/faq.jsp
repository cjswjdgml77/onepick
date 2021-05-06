<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style>
#faq_table{
	margin-top:100px;
	margin-left:200px;
	table-layout:fixed;
	width:800px;
	text-align:center;
}
#faq_table th{
	text-align:center;
}
#bno{
	width:50px;
}
#count{
	width:70px;
}
#write{
	margin-left:200px;
	border:1px solid black;
	text-decoraion:none;
}
.ab a:hover{
	color:none;
	text-decoration:none;
}
.ab a:visited{
	color:black;
}
.ab a{
 display:inline-block;
 width:140px;
}
.modal {
        text-align: center;
}
@media screen and (min-width: 768px) { 
        .modal:before {
                display: inline-block;
                vertical-align: middle;
                content: " ";
                height: 100%;
        }
}
 
.modal-dialog {
        display: inline-block;
        text-align: left;
        vertical-align: middle;
}
#pagination{
	margin-left:-100px;
}
</style>
<script>
$(function (){
	$(".writer").on("click",function(){
		$("#myModal").modal("toggle");
		
		$(".modal-content li").attr("data-writer",$(this).attr("data-writer"));
	});
	
	$("#readById").on("click",function(){
		console.log($(this).data("writer"));
		location.href = "/zboard3/serviceCenter/faq?pageno=1&writer="+$(this).attr("data-writer");
	});
	$("#findJoinDate").on("click",function(){
		$.ajax({url:"/zboard3/users/joinday?username="+$(this).data("writer")})
		.done((joinday)=> alert($(this).data("writer")+"님의 가입일은 "+joinday+" 입니다"));
	});
	$("body").on("click","#writeMemo",function(){
		location.href = "/zboard3/memo/write?receiver=" + $(this).attr("data-writer");
	})
});
</script>
</head>
<body>
	<table class="table table-hover" id="faq_table">
		<tr><th id="bno">번호</th><th style="width: 15px;"><th>제목</th><th>작성일</th><th id="count">조회수</th><th>글쓴이</th></tr>
			<c:forEach items="${page.list }" var="b">
			<tr>
				<td>${b.bno }</td>
				<td>
					<c:if test="${b.attachmentCnt}>0"><i class="fa fa-paperclip"></i></c:if>
				</td>
				<td class="ab"><a href="/zboard3/serviceCenter/faq/read?bno=${b.bno}">${b.title }</a></td>
				<td>${b.writeTimeString}</td>
				<td>${b.readCnt }</td>
				<td class="writer" data-toggle="modal" data-target="#myModal"  data-writer="${b.writer}">${b.writer }</td>
			</tr>
		</c:forEach>
	</table>
	
	<sec:authorize access="isAuthenticated()">
		<a href="/zboard3/serviceCenter/faq/write" id="write" style="color:black; text-decoration:none;">글쓰기</a>
	</sec:authorize>
	<div id="pagination" style="text-align: center;">
		<ul class="pagination">
			<c:if test="${page.prev>0 }">
				<li><a href="/zboard3/board/list?pageno=${page.prev }">이전으로</a></li>
			</c:if>
			
			<c:forEach begin="${page.start }" end="${page.end }" var="i">
				<c:if test="${page.pageno==i}">
					<li class="active"><a href="/zboard3/serviceCenter/faq?pageno=${i }">${i}</a></li>
				</c:if>
				<c:if test="${page.pageno != i }">
					<li><a href="/zboard3/serviceCenter/faq?pageno=${i }">${i}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${page.next>0 }">
				<li><a href="/zboard3/serviceCenter/faq?pageno=${page.next }">다음으로</a></li>
			</c:if>
		</ul>
	</div>
 	<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
     <div class="modal-content" style="width:200px; text-align:center; margin: 0 auto;">
       	<ul style="list-style:none">
       		<li id="readById" >게시물 보기</li>
			<li id="findJoinDate">가입일 보기</li>
			<sec:authorize access="hasRole('ROLE_USER')">
				<li id="writeMemo">메모 보내기</li>
			</sec:authorize>
       	</ul>
      </div>
    </div>
  </div>
</body>
</html>