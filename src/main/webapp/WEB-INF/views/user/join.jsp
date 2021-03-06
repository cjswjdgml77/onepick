<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
body{
    background:#efefef;
}
.form-body{
    background:#fff;
    padding:20px;
}
.login-form{
    background:rgba(255,255,255,0.8);
	padding:20px;
	border-top:3px solid#3e4043;
}
.innter-form{
	padding-top:20px;
}
.final-login li{
	width:50%;
}

.nav-tabs {
    border-bottom: none !important;
}

.nav-tabs>li{
	color:#222 !important;
}
.nav-tabs>li.active>a, .nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus {
    color: #fff;
    background-color: #d14d42;
    border: none !important;
    border-bottom-color: transparent;
	border-radius:none !important;
}
.nav-tabs>li>a {
    margin-right: 2px;
    line-height: 1.428571429;
    border: none !important;
    border-radius:none !important;
	text-transform:uppercase;
	font-size:16px;
}

.social-login{
	text-align:center;
	font-size:12px;
}
.social-login p{
	margin:15px 0;
}
.social-login ul{
	margin:0;
	padding:0;
	list-style-type:none;
}
.social-login ul li{
	width:33%;
	float:left;
    clear:fix;
}
.social-login ul li a{
	font-size:13px;
	color:#fff;
	text-decoration:none;
	padding:10px 0;
	display:block;
}
.social-login ul li:nth-child(1) a{
	background:#3b5998;
}
.social-login ul li:nth-child(2) a{
	background:#e74c3d;
}
.social-login ul li:nth-child(3) a{
	background:#3698d9;
}
#sa-innate-form input[type=text], input[type=password], input[type=file], textarea, select, email{
    font-size:13px;
	padding:10px;
	border:1px solid#ccc;
	outline:none;
	width:100%;
	margin:8px 0;
	
}
#sa-innate-form1 input[type=text], input[type=password], input[type=file], textarea, select, email{
    font-size:13px;
	padding:10px;
	border:1px solid#ccc;
	outline:none;
	width:100%;
	margin:8px 0;
	
}
#sa-innate-form input[type=submit]{
    border:1px solid#e64b3b;
	background:#e64b3b;
	color:#fff;
	padding:10px 25px;
	font-size:14px;
	margin-top:5px;
	}
	#sa-innate-form input[type=submit]:hover{
	border:1px solid#db3b2b;
	background:#db3b2b;
	color:#fff;
	}
	
	#sa-innate-form button{
	border:1px solid#e64b3b;
	background:#e64b3b;
	color:#fff;
	padding:10px 25px;
	font-size:14px;
	margin-top:5px;
	}
	#sa-innate-form button:hover{
	border:1px solid#db3b2b;
	background:#db3b2b;
	color:#fff;
	}
    #sa-innate-form p{
        font-size:13px;
        padding-top:10px;
    }
#sa-innate-form1 input[type=submit]{
    border:1px solid#e64b3b;
	background:#e64b3b;
	color:#fff;
	padding:10px 25px;
	font-size:14px;
	margin-top:5px;
	}
	#sa-innate-form1 input[type=submit]:hover{
	border:1px solid#db3b2b;
	background:#db3b2b;
	color:#fff;
	}
	
	#sa-innate-form1 button{
	border:1px solid#e64b3b;
	background:#e64b3b;
	color:#fff;
	padding:10px 25px;
	font-size:14px;
	margin-top:5px;
	}
	#sa-innate-form1 button:hover{
	border:1px solid#db3b2b;
	background:#db3b2b;
	color:#fff;
	}
    #sa-innate-form1 p{
        font-size:13px;
        padding-top:10px;
    } 
.success {
	color: green;
	font-size: 0.75em;
}

.fail {
	color: red;
	font-size: 0.75em;
}
.top{
	font-family: 'Jua', sans-serif;
	margin:0 auto;
	text-align:center;
	font-size:40px;
	opacity:0.7;
}
.top a:hover{
	color:black;
	text-decoration:none;
}
.top a:visited{
	color:black;
}
</style>
<script>
// ????????? ??????, ????????? ??????, ???????????? ??????, ?????? ??????, ?????? ??????
// : ???????????? ???????????? ???????????? ??? ?????? -> ????????? ???????????? ??? ?????? ??? ???????????? ??????????????? ?????? -> return true
//   value, pattern, msgElement, message
function showSpanError(value,pattern,messageElement,message){
	if(value==""){
		messageElement.text("?????????????????????").attr("class","fail");
		return false;
	}
	if(pattern.test(value)==false){
		messageElement.text(message).attr("class","fail");
		return false;
	}
}
function usernameCheck(){
	var $username = $("#username").val();
	var pattern = /^[a-z]+[a-z0-9]{5,19}$/;
	return showSpanError($username,pattern,$("#username_msg"),"???????????? ???????????? ???????????? 6~20??? ????????? ?????? ???????????????")
}
function irumCheck(){
	$("#irum_msg").text("");
	var $irum = $("#irum").val();
	var pattern = /^[???-???]{2,}$/;
	return showSpanError($irum,pattern,$("#irum_msg"),"????????? ?????? 1?????? ???????????????");
}
function emailCheck(){
	var $email = $("#email").val();
	var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	return showSpanError($email,pattern,$("#email_msg"),"????????? ???????????? ???????????????");
}
function birthdayCheck(){
	$("#birthday_msg").text("");
	var $birthday = $("#birthday").val();
	var pattern = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
	return showSpanError($birthday,pattern,$("#birthday_msg"),"????????? ????????? ???????????????");
}
function passwordCheck(){
	$("#password_msg").text("");
	var $password = $("#password").val();
	var pattern = /^[0-9a-zA-Z~!@#$%^&*()_+]{8,10}$/;
	return showSpanError($password,pattern,$("#password_msg"),"??????????????? ???????????? ?????? ???????????? 8~10??? ?????????");
}
function password2Check(){
	$("#passwordCheck_msg").text("");
	var $password2=$("#passwordCheck").val();
	if($password2==""){
		$("#passwordCheck_msg").text("?????? ???????????????").attr("class","fail");
		return false;
	}
	if($password2 === $("#password").val()){
		$("#passwordCheck_msg").text("??????????????? ???????????????").attr("class","success");
		return true;
	}else{
		$("#passwordCheck_msg").text("??????????????? ???????????????").attr("class","success");
		return false;
	}
}
function printMessage(msgElement,message,css){
	msgElement.text(message).attr("class",css)
}
function formSubmit(){
	var formData= new FormData($("#sa-innate-form1")[0]);
	
	$.ajax({
		url: "/zboard3/users/new",
		method: "post",
		data: formData,
		processData: false,
		contentType: false
	}).done(()=>Swal.fire({
		  position: 'top-end',
		  icon: 'success',
		  title: '????????? ?????????????????????!',
		  showConfirmButton: false,
		  timer: 1500
		})).fail((msg)=>Swal.fire({
			  position: 'top-end',
			  icon: 'error',
			  title: msg,
			  showConfirmButton: false,
			  timer: 15000
			}));
	
}
$(document).ready(function(){
	var $msg = '${msg}';
	if($msg!="")
		alert($msg);
	$("#username").on("blur", usernameCheck);
	$("#irum").on("blur", irumCheck);
	$("#email").on("blur",emailCheck);
	$("#birthday").on("blur",birthdayCheck);
	$("#password").on("blur",passwordCheck);
	$("#passwordCheck").on("blur",password2Check);
	
	var usernameUrl = "/zboard3/users/user/username?username=";
	$("#username").on("blur", function(){
		if(usernameCheck()==false)
			return false;
		$.ajax(usernameUrl+$("#username").val())
		.done(()=>printMessage($("#username_msg"),"?????? ??????????????????","success"))
		.fail(()=>printMessage($("#username_msg"),"???????????? ??????????????????","fail"));
	});
	var emailUrl = "/zboard3/users/user/email?email=";
	$("#email").on("blur", function(){
		if(emailCheck()==false)
			return false;
		$.ajax(emailUrl+$("#email").val())
			.done(()=>printMessage($("#email_msg"),"?????? ??????????????????","success"))
			.fail(()=>printMessage($("#email_msg"),"???????????? ??????????????????","fail"));
	});
	$("#join").on("click", function(){
		var r1 = usernameCheck();
		var r2 = irumCheck();
		var r3 = birthdayCheck();
		var r4 = passwordCheck();
		var r5 = password2Check();
		var r6 = emailCheck();
		if( r1&&r2&&r3&&r4&&r5&&r6==false)
			return false;
		$.when($.ajax(usernameUrl+$("#username").val()), $.ajax(emailUrl+$("#email").val()))
		.done(()=>formSubmit()).fail(()=>Swal.fire({
			  position: 'top-end',
			  icon: 'error',
			  title: '????????? ??????????????????',
			  showConfirmButton: false,
			  timer: 15000
			}))
	});
	
})
</script>
</head>
<body>
	<div class="top"><a href="/zboard3/">
	O N E P I C K</a></div>
<br>
<br>
<div class="container">
<div class="row">
<div class="col-md-4 col-md-offset-4">
<div class="form-body">
    <ul class="nav nav-tabs final-login">
        <li class="active"><a data-toggle="tab" href="#sectionA">Sign In</a></li>
        <li><a data-toggle="tab" href="#sectionB">Join us!</a></li>
    </ul>
    <div class="tab-content">
        <div id="sectionA" class="tab-pane fade in active">
        <div class="innter-form">
            <form id="sa-innate-form" action="/zboard3/user/login" method="post">
            <label>?????????</label>
            <input type="text" name="username">
            <label>????????????</label>
            <input type="password" name="password">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit" id="sign">Sign In</button>
            <a href="/zboard3/user/find">Forgot Password?</a>
            </form>
            </div>
            <div class="social-login">
            <p>- - - - - - - - - - - - - Sign In - - - - - - - - - - - - - </p>
    		
            </div>
        </div>
  		
        <div id="sectionB" class="tab-pane fade">
			<div class="innter-form">
            <form id="sa-innate-form1">
            <label>?????????</label><span id="username_msg"></span>
            <input type="text" name="username" id="username" class="joinData">
            <label>??????</label><span id="irum_msg"></span>
            <input type="text" name="irum" id="irum" class="joinData">
            <label>?????????</label><span id="email_msg"></span>
            <input type="text" name="email" id="email" class="joinData">
            <label>??????</label><span id="birthday_msg"></span> <br>
            <input type="date" name="birthday" id="birthday" class="joinData"><br>
            <label>????????????</label><span id="password_msg"></span>
            <input type="password" name="password" id="password" class="joinData">
            <label>???????????? ??????</label><span id="passwordCheck_msg"></span>
            <input type="password" id="passwordCheck">
            <button type="button" id="join">Join now</button>
            <p>By clicking Join now, you agree to hifriends's User Agreement, Privacy Policy, and Cookie Policy.</p>
            </form>
            </div>
            <div class="social-login">
            <p>- - - - - - - - - - - - - Register - - - - - - - - - - - - - </p>
			
            </div>
        </div>
    </div>
    </div>
    </div>
    </div>
    </div>                                                                 
</body>
</html>