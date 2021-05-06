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
	margin: 0 auto;
	text-align:center;
	margin-top:100px;
}
#input2Form{
	margin-top:200px;
}
.findById p {
		font-size:3.00em;
		font-weigth:bolder;
		font-family: 'Jua', sans-serif;
}
.findInfo {
	opacity:0.5;
}
.findInput{
	margin-top:80px;
}
.findInput input{
	width:300px;
	margin-right:70px;
	margin-left:70px;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:0.25px solid ;
	font-family: 'Jua', sans-serif;
}

.findInput input:focus, select:focus, option:focus, textarea:focus, button:focus{
	outline: none;
}
.findButton{
	font-weight:bolder;
	border-line:none;
	width:300px;
	font-size:1.30em;
	height:50px;
	border:0;
	outline:0;
	background-color:lightgrey;
	margin-top:30px;
	font-family: 'Jua', sans-serif
}
</style>
<script>
$(document).ready(function(){
	$("#findButton1").on("click",function(){
		$.ajax(
			"/zboard3/users/username/email?email="+$("#findEmail1").val())
			.done((msg)=>Swal.fire({position: 'top-end', icon: 'success',title:"아이디를 찾았습니다",text:"아이디는 : " + msg
			})).fail(()=>Swal.fire({position: 'top-end', icon: 'fail',title: "이름 또는 이메일을 확인해주세요"}));
	});
	$("#findButton2").on("click",function(){
		//$("#input2Form").attr("action","/zboard3/users/reset_password").attr("method","post").submit();
		var params = {
			username:$("#findUsername2").val(),
			email:$("#findEmail2").val(),
			_csrf:'${_csrf.token}'
		}
		console.log(params)
		$.ajax({
			url: "/zboard3/users/username/password",
			data: params,
			success: function any(){
				location.href="/zboard3/user/login"
			},
			error: function dsaf(){
				Swal.fire({position: 'top-end', icon: 'fail',title: "아이디 또는 이메일을 확인해주세요"})
			}
		});
	});
})
</script>
</head>
<body>
	<div id="findPage">
		<form  id="input1Form">
			<div class="findById">
				<p>아이디 찾기</p>
			</div>
			<div class="findInfo">
				<p>가입하신 이름과 이메일 통해 아이디를 확인하실 수 있습니다</p>
			</div>
			<div class="findInput">
				<input name="username" placeholder="아이디"> <input name="email" id="findEmail1" placeholder="이메일">
			</div>
			<button type="button" id="findButton1" class="findButton">확인</button>
		</form>
		
		<form id="input2Form">
			<div class="findById">
				<p>임시 비밀번호 발급</p>
			</div>
			<div class="findInfo">
				<p>가입하신 이름과 이메일  입력후 이메일을 통해 임시 비밀번호를 보내드립니다.
						로그인 후 반드시 비밀번호를 변경하시기 바랍니다.</p>
			</div>
			<div class="findInput">
				<input type="hidden" name=_csrf value="${_csrf.token }">
				<input name="username" placeholder="아이디" id="findUsername2"> <input name="email" id="findEmail2" placeholder="이메일">
			</div>
			<button type="button" id="findButton2" class="findButton">확인</button>
		</form>
	</div>
</body>
</html>
