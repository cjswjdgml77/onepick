<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
#top2{
	font-family: 'Jua', sans-serif;
	margin:0 auto;
	text-align:center;
	font-size:40px;
	opacity:0.7;
	margin-top:150px;
}

#info_table table{
	margin-left: auto; margin-right: auto;
	width:600px;
	height:300px;
}

#info .front{
	width:100px;
	text-align:center;
}
.profile{
	margin-left:300px;
	margin-bottom:5px;
}
#update{
	margin-left:350px;
}

</style>
<script>
function loadProfile() {
	console.log('2');
	var file = $("#profile")[0].files[0];
	var maxSize = 1024*1024;			
	if(file.size>maxSize) {
		Swal.fire('프로필 크기 오류', '프로필 사진은 1MB를 넘을 수 없습니다','error');
		$("#profile").val("");
		$("#show_profile").removeAttr("src");
		return false;
	}
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function() {
		$("#show_profile").attr("src", reader.result);
	}
	return true;
}
function makePage(){
	$("#passwordArea").hide();
	$("#activateChangePwd").on("click",function(){
		$("#passwordArea").toggle();
	});
	var email = '${user.email}'.split('@');
	$("#email1").val(email[0]);
	$("#email2").val(email[1]).prop("disabled",true);
	
	var isFind= false;
	var $select = $("#selectEmail option");
	$.each($select, function(idx,option){
		if($(option).text()===email[1]){
			$(option).prop("selected",true);
			isFind=true;
		}
	});
	if(isFind==false)
		$("#email").prop("disabled",false);
	
}
$(document).ready(function(){
	makePage();
	$("#profile").on("change", loadProfile);
	$("#selectEmail").on("change",function(){
		var $selectOption = $("#selectEmail").val();
		if($selectOption=="직접 입력")
			$("#email2").prop("disabled",false).val(" ").attr("placeholder","직접 입력").focus();
		else 
			$("#email2").val($selectOption);
	});
	
	$("#changeIrum").on("click",function(){
		var params={
				irum:$("#irum").val(),
				_method:"patch",
				_csrf:"${_csrf.token}"
			}
		$.ajax({
			url:"/zboard3/users/username",
			data:params,
			method:"post"
		}).done(()=>alert("성 공")).fail(()=>alert("실 패"));
	});
	
	$("#changePwd").on("click",function(){
		$password = $("#password").val();
		$newPwd= $("#newPassword").val();
		$newPwd2= $("#newPassword2").val();
		
		var pattern1 = /(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$/;
		var pattern2 = /^[A-Za-z0-9]{20}$/;
		if(pattern1.test($password)==false && pattern2.test($password)==false){
			alert("비밀번호를 확인하세요");
			return false;
		}
		if(pattern1.test($newPwd)==false){
			alert("새 비밀번호는 특수문자를 포함하는 영숫자와 특수문자 8~10자 입니다");
			return false;
		}
		if($newPwd!=$newPwd2){
			alert("새 비밀번호가 일치하지 않습니다");
			return false;
		}/*
		var params={
				password:$password,
				newPassword:$newPwd,
				_csrf: "${_csrf.token}",
				_method: "patch" 
			}
		$.ajax({
			url:"/zboard3/users/password",
			method:"post",
			data:params
		}).done(()=>alert("성 공")).fail(()=>alert("실 패")); */
	});
	$("#update").on("click",function(){
		var formData = new FormData();
		if($("#profile")[0].files[0]!=undefined)
			formData.append("profile", $("#profile")[0].files[0]);
		formData.append("irum", $("#irum").val());
		formData.append("email",$("#email1").val()+"@"+$("#email2").val());
		formData.append("_csrf", "${_csrf.token}");
		
		var $password = $("#password").val();
		var $newPwd = $("#newPassword").val();
		var $newPwd2 = $("#newPassword2").val();
		var pattern = /^[0-9a-zA-Z~!@#$%^&*()_+]{8,10}$/;
		if(pattern.test($newPwd)==true && $newPwd==$newPwd2){
			formData.append("password",$("#password").val());
			formData.append("newPassword",$newPwd);
		}
		for(var pair of formData.entries())
			console.log(pair[0] + ' ' + pair[1])
		$.ajax({
				url : "/zboard3/users/alter",
				data : formData,
				method: "post",
				processData: false,
				contentType: false
			}).done(()=>alert("성 공")).fail(()=>alert("실 패"));		
	});
	$("#resign").on("click",function(){
		if(confirm("정말로 탈퇴하시겠습니까?")==false)
			return;
		var $form = $("<form>").attr("action","/zboard3/user/resign").attr("method","post").appendTo($("body"));
		$("<input>").attr("type","hidden").attr("name","_csrf").val("${_csrf.token}").appendTo($form);
		$form.submit();
	});
	
})
</script>
</head>
<body>
	<div id="top2" >회원정보수정</div>
	<div id="info_table" >
	<div class="profile">
		<img id="show_profile" height="100px;" src="http://localhost:8081/profile/${user.profile }">
	</div>
	<div class="profile">
		<input type="file" name="profile" id="profile">
	</div>
	<table id="info" class="table table-bordered">
		<tr>
			<td class="front">이름</td>
			<td><input type="text" id="irum" value="${user.irum }"> <button type="button" id="changeIrum">이름변경</button></td>
			<td></td>
		</tr>
		<tr>
			<td class="front">생년월일</td><td colspan="2"><span id="birthday">${user.birthdayString }</span> </td>
		</tr>
		<tr>
			<td class="front">가입일</td><td colspan="2"><span id="joinday">${user.joindayString}</span></td>
		</tr>
		
		<tr><td class="front">비밀번호</td>
			<td colspan="2">
				<button type="button" id="activateChangePwd">비밀번호 수정</button>
				<div id="passwordArea">
					<span class="key">현재 비밀번호 : </span><input type="password" id="password" ><br>
					<span class="key">새 비밀번호 : </span><input type="password" id="newPassword"><br>
					<span class="key">새 비밀번호 확인 : </span><input type="password" id="newPassword2">
	  				<button type="button" id="changePwd">변경</button>
				</div>
			</td></tr>
		<tr><td class="front">이메일</td>
			<td colspan="2">
				<input type="text" name="email1" id="email1">&nbsp;@&nbsp;<input type="text" name="email2" id="email2">&nbsp;&nbsp;
				<select id="selectEmail">
					<option selected="selected">직접 입력</option>
					<option>naver.com</option>
					<option>daum.net</option>
					<option>gmail.com</option>
				</select>
			</td></tr>
	</table>
	<button type="button" class="btn btn-success" id="update">변경하기</button>
	<button type="button" class="btn btn-alert" id="resign">탈퇴하기</button>
	</div>
</body>
</html>