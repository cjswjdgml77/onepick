package com.icia.zboard3.controller.mvc;

import java.security.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.icia.zboard3.dto.*;
import com.icia.zboard3.service.mvc.*;
import com.sun.istack.*;

import lombok.*;
import oracle.jdbc.proxy.annotation.*;
@RequiredArgsConstructor
@Controller
public class CartController {
	private final CartService service;
	@GetMapping("/cart/read")
	public ModelAndView read() {
		return new ModelAndView("main").addObject("viewname","page/product/cart_list.jsp");
	}
	// 장바구니에 추가. 장바구니에 존재하면 개수 증가, 없으면 추가
	@PostMapping(value="/carts" , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carts(@NonNull Integer pno, HttpSession session) {
		return ResponseEntity.ok(service.addCart(pno,session));
	}
	// 개수 변경
	@PatchMapping(value="/carts",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> carts(@NotNull Integer pno,@NonNull Boolean isIncrease, HttpSession session){
		return ResponseEntity.ok(service.change(pno,isIncrease,session));
	}
	// 상품 삭제
	@DeleteMapping(value="/carts",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> delete(@RequestParam(value="pnos[]") List<Integer> pnos,HttpSession session){
		System.out.println(pnos);
		return ResponseEntity.ok(service.delete(pnos,session));
	}
}
