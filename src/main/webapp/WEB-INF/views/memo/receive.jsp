<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<style>
#list {
	margin-top:30px;
	margin-left:30px;
	width: 800px;
}
#th_read{
	text-align:center;
}
.isRead{
	text-align:center;
}
</style>
<sec:authorize access="hasRole('ROLE_USER')">
	<script src="/zboard3/script/wsocket.js"></script>
</sec:authorize>
<script>
	$(function() {
		$("#delete").on("click", function() {
			var ar = "";
			$(".mno").each(function(idx) {
				if($(this).prop("checked")) 
					ar = ar + $(this).val() + ","
			});
			// 체크된 체크박스가 없을 경우 작업 중단
			if(ar.length==0)
				return;
			
			var params ={
				mnos: ar.substring(0, ar.lastIndexOf(",")),
				_csrf:"${_csrf.token}"
			}
			$.ajax({
				url:"/zboard3/memo/disabled_by_receiver",
				method:"post",
				data: params,
				success:function(result) {
					location.reload();
				}
			})
			
		});
	})
</script>
</head>
<body>
<div id="page">
	<div id="main">
		<table id="list" class="table table-hover">
			<colgroup>
				<col width="20%"><col width="40%"><col width="20%"><col width="10%"><col width="10%">
			</colgroup>
			<thead>
				<tr><th>보낸이</th><th>제목</th><th>보낸 날짜</th><th id="th_read">읽음</th><th></th>
			</thead>
			<c:forEach items="${memos}" var="memo">
				<tr>
					<td>${memo.sender}</td>
					<td><a href="/zboard3/memo/read?mno=${memo.mno}">${memo.title}</a></td>
					<td>${memo.writeTimeString}
					<td class="isRead">
						
						<c:choose>
							<c:when test="${memo.isRead==true}">O</c:when>
							<c:otherwise>X</c:otherwise>
						</c:choose>
					</td>
					<td><input type="checkbox" class="mno" value="${memo.mno}"></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<button id="delete">체크한 메모 삭제</button>
</div>
</body>
</html>