<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
#scdiv{
	text-align:center;
	margin:0 auto;
}
#scul{
	list-style:none;
	width:1000px;
	margin-top:150px;
	margin-left:100px;
	
	
}
#scdiv .scli{
	opacity:0.5;
	color:black;
	width:200px;		
	display:inline; 
	margin:20px;
	
}
#scdiv .scli a:hover,:link{
	opacity:1;
	color:black;
	text-decoration:none;
}
#scdiv .scli a:visited{
	opacity:1;
	color:write;
	background-color:black;
	text-decoration:none;
}

</style>
</head>
<body>
	<div id=scdiv>
		<ul id="scul">
			<li class="scli"><a href="#">공지사항</a></li>
			<li class="scli"><a href="/zboard3//user">FAQ</a></li>
			<li class="scli"><a href="#">EVENT</a></li>
			<li class="scli"><a href="#">교환/반품문의</a></li>
			<li class="scli"><a href="#">상품문의</a></li>
			<li class="scli"><a href="#">배송문의</a></li>
			<li class="scli"><a href="#">신고</a></li>
		</ul>
	</div>
	<div id="list">
		<jsp:include page="${service}"></jsp:include>
	</div>
	
</body>
</html>