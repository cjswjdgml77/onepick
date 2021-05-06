<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
function printList(list){
	console.log(list);
	var $main_div = $(".product_div");
	$main_div.empty();
	$.each(list,function(idx,btm){
		var $outter_div=$("<div>").css("width","176px").css("margin-right","25px").css("display","inline-block").appendTo($main_div);
		$("<img>").attr("src",btm.image).css("width","175px").appendTo($outter_div);
		var $inner_div=$("<div>").appendTo($outter_div);
		$("<span>").css("width","175px").text(btm.price+"원").appendTo($inner_div);
		$("<span>").css("font-size","0.75em").text(btm.name).appendTo($inner_div);
		var $cart_div = $("<div>").appendTo($outter_div);
		$("<button>").attr("class","cart").attr("data-pno",btm.pno).text("장바구니 담기").appendTo($cart_div);
		
	});
	
}
$(function(){
	$("#wholeList").on("click",function(){
		$.ajax({
			url:"/zboard3/products"
		}).done((list)=>{printList(list)});
	});
	$("#tops").on("click",function(){
		var cnos=[11,12];
		var param = {cnos:cnos};
		$.ajax({
			url:"/zboard3/products/tops",
			data:param
		}).done((list)=> {printList(list)});
	});
	$("#bottoms").on("click",function(){
		var cnos= [21,22];
		var param ={cnos:cnos};
		$.ajax({
			url:"/zboard3/products/bottoms",
			data:param
		}).done((list)=> {printList(list)});
	})
	$(".product_div").on("click",".cart",function(){
		console.log($(this));
		var params={
				pno:$(this).attr("data-pno"),
				_csrf: "${_csrf.token}"
		}
		console.log(params);
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
<style>
.product_div{
	margin-left:150px;
	margin-top:20px;
}

#wholeList{
	cursor:pointer;;
}
#tops{
	cursor:pointer;
}
#bottoms{
	cursor:pointer;
}
</style>
</head>
<body>
		<div id="main_advertisement">
			<div id="main_board_ad1">봄 맞이 초특가 세일<br>
			 최대 ~50%</div>
			<div id="main_board_ad2">신규고객 NEW쿠폰</div>
		</div>
		<div id="main_board">
			<div>~50% 세일</div><br>
		</div>
			<div id="main_list">
				<ul>
					<li id="wholeList">전체보기</li>
					<li id="tops">상의</li>
					<li>아우터</li>
					<li id="bottoms">하의</li>
				</ul>
			</div>
		
		<div class="product_div">
		<c:forEach items="${list}" var="product" varStatus="status">
		<div style="width: 176px; margin-right: 25px; display: inline-block;">
			<img src="${product.image}" width="175px">
			<div>
				<span style="width: 175px;">${product.price }원</span>
				<span style="font-size: 0.75em;">${product.name }</span>
			</div>
			<div id="cartArea">
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