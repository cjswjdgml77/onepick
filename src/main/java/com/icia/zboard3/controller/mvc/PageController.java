package com.icia.zboard3.controller.mvc;

import java.util.*;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.icia.zboard3.entity.*;
import com.icia.zboard3.service.mvc.*;

import lombok.*;
@RequiredArgsConstructor
@Controller
public class PageController {
	private final ProductService service;
	@GetMapping("/")
	public ModelAndView mainPage() {
		return new ModelAndView("main").addObject("viewname","page/start.jsp")
				.addObject("list",service.list());
	}
	@GetMapping("/products/bottoms")
	public ResponseEntity<?> bottomsPage(@RequestParam(value="cnos[]") List<Integer> cnos) {
		System.out.println(cnos);
		List<Product> products = service.bottomsPage(cnos);
		System.out.println(products);
		return ResponseEntity.ok(products);
	}
	@GetMapping("/products/tops")
	public ResponseEntity<?> topsPage(@RequestParam(value="cnos[]") List<Integer> cnos){
		List<Product> products = service.topsPage(cnos);
		return ResponseEntity.ok(products);
	}
	@GetMapping("/products")
	public ResponseEntity<?> mainListPage(){
		List<Product> products =service.mainListPage();
		return ResponseEntity.ok(products);
	}
}
