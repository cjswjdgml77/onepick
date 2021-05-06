<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
</script>
<style>
#product_div{
	margin-left:150px;
	margin-top:20px;
}
</style>
<script>
$(function(){
	$(".cart").on("click",function(){
		var params={
				pno:$(this).attr("data-pno"),
				_csrf: "${_csrf.token}"
		}
		$.ajax({
			url:"/zboard3/carts",
			data:params,
			method:"post"
		}).done(()=>{ var choice = confirm("장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?")
				if(choice==true)
				location.href = "/zboard3/cart/read"
		}).fail(()=>alert("장바구니에 담는 것을 실패했습니다"));
	});
})
</script>
</head>
<body>
	
	<div id="product_div">
		<c:forEach items="${list}" var="product" varStatus="status">
		<div style="width: 176px; margin-right: 25px; display: inline-block;">
			<img src="${product.image}" width="175px">
			<div>
				<span style="width: 175px;">${product.price }원</span>
				<span style="font-size: 0.75em;">${product.name }</span>
			</div>
			<div>
				<button class="cart" data-pno="${product.pno}">장바구니 담기</button>
			</div>
		</div>
		<c:if test="${status.count ==5}">
			<hr>
		</c:if>
	</c:forEach>
	</div>
</body>
</html>