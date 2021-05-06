<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/zboard3/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
#top{
	margin:0 auto;
	margin-top:100px;
	text-align:center;
	font-size:3.00em;
}
#readForm{
	border-top: 1px solid #ccc;
	margin-left:100px;
	margin-top:50px;
	

}
#upper{
	margin-top:10px;
	margin-bottom:10px;
	
}
.front{
	text-align:center;
	display:inline-block;
	margin-right:5px;
	margin-bottom:10px;
	width:100px;
	background-color:rgb(220,220,220,0.5);
}
#title{
	width:400px;
}
#content{
	min-height:300px;

}
input:disabled{
	border:none;
	background-color:white;
}
</style>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.username" var="username"/>
	<script>
		var isLogin = true;
		var isWriter = ${board.writer==username};
	</script>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	<script>
		var isLogin = false;
		var isWriter = false;
	</script>
</sec:authorize>
<script>
function printComments(dto){
	// 다음 댓글 출력 영역
	console.log(dto.nextCommentPage);
	var $comments = $("#comments");
	$("#nextCommentPage").remove();
	
	$.each(dto.comments, function(idx,comment){
		var $outer_div=$("<div>").appendTo($comments)
		var $upper_div=$("<div>").appendTo($outer_div);
		var $lower_div=$("<div>").css("overflow","hidden ").appendTo($outer_div);
		$("<span>").text(comment.writer).css("color","blue").appendTo($upper_div);
		$("<span>").text(" "+comment.writeTime).appendTo($upper_div);
		$("<img>").attr("src",comment.profile).css("width","60px").appendTo($lower_div);
		$("<span>").text(comment.content).appendTo($lower_div);
		
		if(isLogin == true && comment.writer=='${username}'){
			$("<button>").attr("class","delete_comment").data("cno",comment.cno).data("bno",comment.bno)
			.css("float","right").text("삭 제").appendTo($lower_div);
		}
	});
	if(dto.nextCommentPage>0)
		$("<button>").text("다음 댓글").data("pageno", dto.nextCommentPage).attr("id","nextCommentPage").appendTo($("#next"));
	
}
function printAttachments(attachments){
	var $attachment = $("#attachment");
	$attachment.empty();
	$.each(attachments, function(idx,attachment){
		var $li =$("<li>").css("overflow","hidden").css("width","300px").appendTo($attachment);
		var $div = $("<div>").css("float","left").appendTo($li);
			if(attachment.isImage==true)
				$("<i class='fa fa-file-image-o'></i>").appendTo($div);
			else
				$("<i class='fa fa-paperclip'></i>").appendTo($div);
			
			$("<a>").attr("href","/zboard3/attachments/"+attachment.ano).text(attachment.originalFileName).appendTo($div);
			
		var $div2 =$("<div>").css("float","right").appendTo($li);
			if(isWriter==true){
				console.log(attachment.originalFileName);
				$("<span>").attr("class","delete_attachment").data("ano",attachment.ano).data("bno",attachment.bno)
				.attr("title",attachment.originalFileName+" 삭제").css("cursor","pointer").text("X").appendTo($div2);
				}
	});
}
$(document).ready(function(){
	if(isLogin == true && isWriter==true){
		console.log("d");
		$("#title").prop("disabled", false);
		var ck = CKEDITOR.replace('content',{
			filebrowserUploadUrl : '/zboard3/boards/image?_csrf=${_csrf.token}'
		});
		var $btnArea = $("#btnArea");
		$("<button>").attr("id","update").attr("class","btn btn-info").text("변 경").appendTo($btnArea);
		$("<button>").attr("id","delete").attr("class","btn btn-info").text("삭 제").appendTo($btnArea);
		
		$("#comment_textarea").prop("readonly",false).attr("placeholder", "욕설이나 모욕적인 댓글은 삭제될 수 있습니다");
		var $commentBtnArea = $("#commentBtnArea");
		$("<button>").attr("id","write").attr("class","btn btn-info").text("댓글 추가").appendTo($commentBtnArea);
	}else if(isLogin == true && isWriter ==false){
		$("#good_btn").prop("disabled",false);
		$("#bad_btn").prop("disabled",false);
		$("#comment_textarea").prop("readonly",false).attr("placeholder","욕설이나 모욕적인 댓글은 삭제될 수 있습니다");
		var $commentBtnArea = $("#commentBtnArea");
		$("<button>").attr("id","write").attr("class","btn btn-info").text("댓글 추가").appendTo($commentBtnArea);
	}
	//댓글작성
	$("#commentBtnArea").on("click","#write",function(){
		var params = {bno:'${board.bno}',content:$("#comment_textarea").val(), _csrf:'${_csrf.token}'};
		$.ajax({
			url:'/zboard3/comments',method:"post",data:params
		}).done((map)=>{
			$("#comment_textarea").val("");
			$("#comments").empty();
			printComments(map);
		});
		
	});
	// 첨부파일 삭제
	$("#attachment").on("click",".delete_attachment",function(){
		console.log("첨부파일 삭제 실행");
		var params=  {bno: $(this).data("bno"), _csrf: "${_csrf.token}", _method: "delete"};
		$.ajax({url:"/zboard3/attachments/"+$(this).data("ano"), data:params, method:"post"})
		.done(attachments=>printAttachments(attachments));
	});
	// 추천
	$("#good_btn").on("click",function(){
		var params = {bno:'${board.bno}',isGood:1, _csrf: "${_csrf.token}", _method: 'patch'}
		$.ajax({
			url:'/zboard3/board/goodOrBad',data:params,method:'post'
		}).done((cnt)=>$("#goodCnt").text(cnt)).fail(()=>alert("추천에 실패했습니다"));
	});
	
	// 비추천
	$("#bad_btn").on("click",function(){
		var params = {bno:'${board.bno}',isGood:0,_csrf: '${_csrf.token}', _method: 'patch'}
		$.ajax({
			url:'/zboard3/board/goodOrBad',data:params,method:'post'
		}).done((cnt)=>$("#badCnt").text(cnt)).fail(()=>alert("비추천에 실패했습니다"));
	});
	//다음 댓글 버튼
	$("#next").on("click","#nextCommentPage",function(){
		console.log("/dsafdsaf");
		var params = {pageno:$(this).data("pageno"), bno:'${board.bno}'}
		$.ajax({url:"/zboard3/comments/next", data:params}).done((dto)=>{
			printComments(dto);
		})
	});
	//댓글 삭제
	$("#comments").on("click",".delete_comment",function(){
		var params = {cno:$(this).data("cno"), bno:'${board.bno}',_csrf:'${_csrf.token}',_method:"delete"}
		$.ajax({url:"/zboard3/comments/delete",method:"post",data:params}).done((dto)=>{
			$("#comments").empty();
			printComments(dto);
		})
	});
	//글수정
	$("#btnArea").on("click","#update",function(){
		var $form = $("<form>").attr("action","/zboard3/serviceCenter/faq/update").attr("method","post");
		$("<input>").attr("type","hidden").attr("name","bno").val('${board.bno}').appendTo($form);
		$("<input>").attr("type","hidden").attr("name","title").val($("#title").val()).appendTo($form);
		$("<input>").attr("type","hidden").attr("name","content").val(CKEDITOR.instances['content'].getData()).appendTo($form);
		$("<input>").attr("type","hidden").attr("name","_csrf").val('${_csrf.token}').appendTo($form);
		$form.appendTo($("body")).submit();
	});
	//글삭제
	$("#btnArea").on("click","#delete",function(){
		var $form = $("<form>").attr("action","/zboard3/serviceCenter/faq/delete").attr("method","post");
		$("<input>").attr("type","hidden").attr("name","bno").val('${board.bno}').appendTo($form);
		$("<input>").attr("type","hidden").attr("name","_csrf").val('${_csrf.token}').appendTo($form);
		
		$form.appendTo($("body")).submit();
	})
})

</script>
</head>
<body>
<p id="top">FAQ</p>
<div id="readForm">
	<div id="upper"> 
		<span class="front">제목&nbsp; : </span><input type="text" id="title" disabled="disabled" value="${board.title }"><br>
		<span class="front">작성자&nbsp; : </span><span>${board.writer }</span>
	</div>
	<div id="lower">
		<span class="front">글번호&nbsp; : </span> <span id="bno">${board.bno }</span>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
		<button type="button" class="btn btn-primary" id="good_btn" disabled="disabled">추천<span class="badge" id="goodCnt">${board.goodCnt }</span></button>
		<button type="button" class="btn btn-success" id="b" disabled="disabled">조회 <span class="badge" id="readCnt">${board.readCnt }</span></button>
		<button type="button" class="btn btn-danger" id="bad_btn" disabled="disabled">비추<span class="badge" id="badCnt">${board.badCnt }</span></button>        
		 <br>
		<span class="front">작성일&nbsp; : </span> <span>${board.writeTimeString }</span>
	</div>
	<div>
		<div id="attachment">
			<c:forEach items="${board.attachments }" var="attachment">
				<li style="overflow:hidden; width: 300px;">
					<div style="float:left">
								<c:if test="${attachment.isImage==true }">
									<i class="fa fa-file-image-o"></i>
								</c:if>
								<c:if test="${attachment.isImage==false }">
									<i class="fa fa-paperclip"></i>
								</c:if>
								<a href='/zboard3/attachments/${attachment.ano }'>${attachment.originalFileName}</a>
					</div>
					<div style="float:right;">										
								<c:if test="${board.writer==username}">
									<span class='delete_attachment' data-ano='${attachment.ano}' data-bno='${attachment.bno}' 
										title='${attachment.originalFileName} 삭제' style='cursor:pointer;'>X</span>
								</c:if>
					</div>
				</li>
			</c:forEach>
		</div>
	</div>
	<div id="content_div">
			<div class="form-group">
				<div class="form-control" id="content">${board.content }
				</div>
			</div>
			<div id="btnArea">
			</div>
	</div>
	<div>
		<div class="form-group">
      		<label for="comment_teaxarea">댓글을 입력하세요</label>
      		<textarea class="form-control" rows="5" id="comment_textarea" readonly="readonly"
      			placeholder="댓글을 작성하려면 로그인 해주세요"></textarea>
    	</div>
    	<div id="commentBtnArea">
    	<!-- 로그인하면 댓글 달기 버튼을 추가 -->
		</div>
		<hr>
		<div id="comments">
			<c:forEach items="${board.comments}" var="c">
				<div><span style="color:blue;">${c.writer }</span> ${c.writeTimeString}</div>
					<div style="overflow:hidden;">
						<img src="${c.profile }" style="width:60px;">
						<span>${c.content }</span>
						<c:if test="${c.isWriter==true }">
							<button class="delete_comment" data-cno="${c.cno}" style="float:right;">삭제</button>
						</c:if>
					</div>
			</c:forEach>
		</div>
		<div id="next">
			<c:if test="${board.nextCommentPage>0 }">
				<button id="nextCommentPage" data-pageno="${board.nextCommentPage }">다음 댓글</button>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>