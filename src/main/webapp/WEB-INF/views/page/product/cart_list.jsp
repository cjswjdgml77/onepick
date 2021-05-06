<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
#cart{
	margin-left:100px;
	margin-top:50px;
}
#cart table {
		width: 800px;
		border-collapse: collapse;
		border: 1px solid lightgray;
	}
	#cart .first { width: 50px; }
	#cart .second { width: 150px;  }
	#cart .third { widht: 300px; font-size: 0.8em; }
	#cart .fourth {width: 150px;}
	#cart .fifth { width: 150px; }
	#cart .button_area a, .button_area span { 
		font-size: 0.8em; text-align: center;
		height: 30px; line-height: 30px;
	}
	#cart .price { padding-left: 15px; }
</style>
<script>
$(function(){
	console.log($(".fourth").prev());
	//장바구니 상품 개수 변경
	$(".change").on("click",function(){
		var params={
				pno:$(this).data("pno"),
				_csrf:'${_csrf.token}',
				_method:"patch"
		}
		if($(this).text()=="+")
			params.isIncrease = 1;
		else
			params.isIncrease = 0;
		/**/
		$.ajax({
			url:"/zboard3/carts",
			data:params,
			method:"post"
		}).done((map)=>{
			console.log(map);
			var $parent = $(this).parent();
			console.log($parent.children());
			$parent.children(".count").text(map.cartItem.count);
			console.log(map.cartItem.itemPrice);
			$parent.children(".price").text(map.cartItem.itemPrice+"원");
			$parent.prev().prev().prev().children(".checks").data("count",map.cartItem.count);
			$parent.next().children(".buy").attr("data-count",map.cartItem.count);
			$("#totalPrice").text(map.totalPrice+"원");
		})
	});
	
	$(".delete").on("click",function(){
		var ar=[$(this).data("pno")];
		var params= {
				_csrf : "${_csrf.token}",
				_method : "delete",
				pnos:ar
		};
		$.ajax({
			url:"/zboard3/carts",
			method:"post",
			data: params,
			success:function(result) {
				location.reload();
			}
		})
	});
	$("#check_all").on("click",function(){
		if($(this).prop("checked")==true)
			$(".checks").prop("checked",true);
		else
			$(".checks").prop("checked",false);
			
	});
	$("#buy_all").on("click",function(){
		//var $form = $("<form>").attr("method","post").attr("action","/zboard3/order");
		
		var $checks = $(".checks").prop("checked",true);
		if($checks.length<1)
			return false;
		$.each($checks,function(idx,check){
			var $check = $(check);
			//console.log($check.data("pno"));
			$("<input>").attr("type","hidden").attr("name","pnos").val($check.data("pno")).appendTo($form);
			$("<input>").attr("type","hidden").attr("name","counts").val($check.data("count")).appendTo($form);
		});
		$("<input>").attr("type","hidden").attr("name","_csrf").val($("_csrf.token")).appendTo($form);
		$form.appendTo($("body")).submit();
	});
	
	$("#delete_all").on("click",function(){
		var cnt=0;
		var $unchecked = $(".checks");
		$.each($unchecked,function(idx,uncheck){
			
			var $uncheck = $(uncheck);
			console.log($uncheck.length);
			if($uncheck.prop("checked")==false)
				cnt++;
		});
		console.log(cnt);
		if(cnt==$unchecked.length){	
			return false;
		}
		var $checks = $(".checks").prop("checked",true);
		var $checks =$(".checks")
		var ar =[];
		$.each($checks, function(idx,check){
			var $check = $(check);
			ar.push($check.data("pno"));
			console.log(ar);
		});
		if(ar.length<1)
			return false;
		var params = {pnos:ar, _method:"delete",_csrf: "${_csrf.token}"}
		console.log(params);
		$.ajax({
			url:"/zboard3/carts",
			method:"post",
			data:params,
			success:function(result) {
				location.reload();
			}
		});
	});
})
</script>
</head>
<body>
	<div id="cart">
	<c:if test="${fn:length(cart.cartItemList) == 0}">
		<img src="/product/empty_cart.png">
	</c:if>
	<c:if test="${fn:length(cart.cartItemList) != 0}">
			<div id="cart_area">
			<table>
				<c:forEach items="${cart.cartItemList}" var="item">
					<tr>
						<td class="first">
							<input type="checkbox" class="checks" data-pno="${item.pno}" data-count="${item.count}">
						</td>
						<td class="second">
							<img src="${item.image}" style="width:135px;">
						</td>
						<td class="third">
							${item.name}
						</td>
						<td class="fourth">
							<div class="price">${item.itemPrice}원</div>
							<button class="btn btn-primary change" data-pno="${item.pno}">+</button>
							<span class="count">${item.count}</span>
							<button class="btn btn-primary change" data-pno="${item.pno}">-</button>
						</td>
						<td class="fifth">
							<button class="buy" data-pno="${item.pno}" data-count="${item.count}">구입</button>
							<button class="delete" data-pno="${item.pno}">삭제</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="button_area" style="overflow:hidden;">
			<div style="float:left;">
				<input type="checkbox" id="check_all">전체 선택 
				<button id="delete_all">선택삭제</button>
			</div>
			<div style="float:right;">
				<span id="totalPrice">${cart.totalPrice } 원</span>
				<button type="button" id="buy_all">주문하기</button>
			</div>
		</div>
	</c:if>
</div>
</body>
</html>