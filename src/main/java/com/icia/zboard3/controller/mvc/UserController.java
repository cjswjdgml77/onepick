package com.icia.zboard3.controller.mvc;

import java.security.*;

import javax.servlet.http.*;
import javax.validation.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;

import com.icia.zboard3.dto.*;
import com.icia.zboard3.service.mvc.*;

import lombok.*;
import oracle.jdbc.proxy.annotation.*;
@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService service;
	// View : 회원 가입
	@GetMapping("/user/join")
	public ModelAndView join() {
		return new ModelAndView("/user/join");
	}
	// View : 로그인
	@GetMapping("/user/login")
	public ModelAndView login() {
		return new ModelAndView("/user/login");
	}
	// View : 아이디 찾기
	@GetMapping("/user/find")
	public ModelAndView find() {
		return new ModelAndView("main").addObject("viewname","user/find.jsp");
	}
	// View : 체크코드 
	@GetMapping("/user/join_check")
	public ModelAndView joinCheck(@RequestParam String checkCode) {
		service.joinCheck(checkCode);
		return new ModelAndView("redirect:/user/login");
	}
	// View : 비밀번호 확인
	@GetMapping("/user/password_check")
	public ModelAndView password_check() {
		return new ModelAndView("main").addObject("viewname","user/password_check.jsp");
	}
	// Post : 비밀번호 확인
	@PostMapping("/user/password_check")
	public ModelAndView password_check(@RequestParam String password, HttpSession session, Principal principal) {
		service.passwordCheck(password,principal.getName());
		session.setAttribute("passwordCheck", "true");
		return new ModelAndView("redirect:/user/info");
	}
	// View : 유저정보
	@GetMapping("/user/info")
	public ModelAndView info(HttpSession session, Principal principal, RedirectAttributes ra) {
		if(session.getAttribute("passwordCheck")==null) {
			ra.addFlashAttribute("msg","비밀번호확인이 필요합니다");
			return new ModelAndView("redirect:/user/password_check");
		}
		return new ModelAndView("main").addObject("viewname","user/info.jsp").addObject("user",service.info(principal.getName()));
		
	}
	
	// Post : 회원 탈퇴
	@PostMapping("/users/resign")
	public ModelAndView resign(Authentication auth, SecurityContextLogoutHandler handler, HttpServletRequest req, 
			HttpServletResponse res) {
		service.resign(auth.getName());
		handler.logout(req, res, auth);
		return new ModelAndView("redirect:/");
	}
}
