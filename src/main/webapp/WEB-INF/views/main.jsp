<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet"><!--  
<link rel="stylesheet" href="/zboard3/css/main.css">-->
<script>
</script>
<style>
	* {
	margin: 0;
	padding: 0;
}

#page { 
	width: 1200px;
	height:1200px;
	
}
#header{
	width:1200px;
	margin: 0 auto;
	font-family: 'Jua', sans-serif;
}
#user_section{
	overflow:hidden;
}
#user_section ul{
	list-style:none;
	float:right;
	font-family: 'Jua', sans-serif;
}
#user_section li{
	display:inline-block;
}
#user_section li a:hover{
	text-decoration:none;
}
section{
	width:1200px;
	min-height: 1000px;
	border-top: 1px solid black;
	position:relative;
}
footer {
	border-top: 10px solid rgba(255, 0, 0, 0.5);

}
section #main_advertisement{
	margin-top:30px;
	width:1198px;
	height:200px;
	border : 1px solid skyblue;
	background-color:skyblue;
	text-align:center;
	
	
}
#main_advertisement #main_board_ad1{
	display:block;
	font-size:2em;
	margin-top:40px;
	position:absolute;
	float:left;
	margin-left:300px;
	font-family: 'Jua', sans-serif;
	
}
#main_advertisement #main_board_ad2{
	display:block;
	font-size:1.5em;
	margin-top:150px;
	position:absolute;
	float:right;
	margin-left:800px;
	margin-bottom: 20px;
	font-family: 'Jua', sans-serif;
	
}
#main_board{
	margin-top:50px;
	margin-left:550px;
	font-size:1.25em;
	font-family: 'Jua', sans-serif;
	
}
#main_list ul{
	margin-left:430px;
}
#main_list li{
	display:inline-block;
	padding-right:10px;
	padding-left:10px;
	margin-left:20px;
	list-style:none;
	font-family: 'Jua', sans-serif;
	border: 0.1px solid lightgray;
	text-align:center;
	border-width: thin;
}
#user_ul a{
	color:black;
}
#user_ul a:visited{
	color:black;
	
}
</style>
<script>
	var $msg="${msg}"
	if($msg!="")
		alert($msg);
	$(function() {
		$("#logout").on("click", function() {
			var $input = $("<input>").attr("type","hidden").attr("name","_csrf").val('${_csrf.token}');
			$("<form>").attr("action","/zboard3/user/logout").attr("method","post").append($input).appendTo("body").submit();
		});
	})
</script>

</head>
<body>
<div id="page">
	<header id="header">
		<jsp:include page="include/header.jsp" />
	</header>
	<div id="user_section">
		<ul id="user_ul">
			<sec:authorize access="isAnonymous()">
			<li><a href="/zboard3/user/login">로그인</a></li>
			<li><a href="/zboard3/user/join">회원가입</a></li>
			<li><a href="/zboard3/cart/read">장바구니</a></li>
			<li><a href="/zboard3/serviceCenter/faq">고객센터</a></li>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
			<li><a href="/zboard3/user/info">정보수정</a></li>
			<li><a href="#" id="logout">로그아웃</a></li>
			<li><a href="/zboard3/cart/read">장바구니</a></li>
			<li><a href="/zboard3/memo/send">보낸메모함</a></li>
			<li><a href="/zboard3/memo/receive">받은메모함</a></li>
			<li><a href="/zboard3/serviceCenter/faq">고객센터</a></li>
			</sec:authorize>
		</ul>
	</div>
	<nav>
		<jsp:include page="include/nav.jsp" />
	</nav>
	<div id="main">
		<section>
			<jsp:include page="${viewname}"/>
		</section>
	</div>
	<footer>
		<jsp:include page="include/footer.jsp" />
	</footer>
</div>
</body>
</html>