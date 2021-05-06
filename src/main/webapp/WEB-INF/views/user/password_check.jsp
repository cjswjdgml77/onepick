<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Jua&family=Roboto+Condensed:wght@700&display=swap" rel="stylesheet">
<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<style>
#findPage{
	text-align:center;
	margin-top:100px;
	display:block;
}
#input2Form{
	margin-top:200px;
}
.findById p {
		font-size:3.00em;
		font-weigth:bolder;
		font-family: 'Jua', sans-serif;
	}
#findPage label{
	font-size:3.00em;
	font-weigth:bolder;
	font-family: 'Jua', sans-serif;
	margin-bottom:50px;
}
#findPage input{
	width:300px;
	margin-top:40px;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:0.25px solid ;
	font-family: 'Jua', sans-serif;
	display:block;
	margin: 0 auto;
}

#password input:focus, select:focus, option:focus, textarea:focus, button:focus{
	outline: none;
}
#passwordCheck{
	font-weight:bolder;
	border-line:none;
	width:150px;
	font-size:1.30em;
	height:50px;
	border:0;
	outline:0;
	background-color:lightgrey;
	margin-top:30px;
	font-family: 'Jua', sans-serif
}
</style>

</head>
<body>
	<form action="/zboard3/user/password_check" method="post">
		<div id="findPage">
			<label for="password">비밀번호 확인</label>
			<input id="password" type="password" name="password" class="form-control">
			<span class="helper-text" id="password_msg"></span>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<button class="btn btn-success" id="passwordCheck">비밀번호 확인</button>
		</div>
	</form>
</body>
</html>


