package com.icia.zboard3.controller.mvc;


import java.security.*;
import java.util.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.*;
import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.service.mvc.*;
import com.icia.zboard3.util.*;
import com.sun.istack.*;

import lombok.*;
@RequiredArgsConstructor
@Controller
public class ServiceCenterController {
	private final FaqBoardService service;
	@GetMapping("/serviceCenter")
	public ModelAndView mainPage() {
		return new ModelAndView("main").addObject("viewname",Zboard3Constant.ServicePath);
	}
	// 페이징 : 게시판 출력
	@GetMapping("/serviceCenter/faq")
	public ModelAndView faq(@RequestParam(defaultValue = "1") Integer pageno, String writer) {
		return new ModelAndView("main").addObject("viewname",Zboard3Constant.ServicePath).addObject("service","service/faq.jsp").addObject("page",service.list(pageno,writer));
	}
	// View : 글쓰기 출력
	@Secured("ROLE_USER")
	@GetMapping("/serviceCenter/faq/write")
	public ModelAndView faqWrite() {
		return new ModelAndView("main").addObject("viewname","serviceCenter/service/write.jsp");
	}
	// Post : 글쓰기
	@Secured("ROLE_USER")
	@PostMapping("/serviceCenter/faq/write")
	public ModelAndView faqWrite(@ModelAttribute @Valid FaqBoardDto.Write dto, BindingResult bindingResult,@RequestParam List<MultipartFile> attachments,Principal principal) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.write(dto,attachments,principal.getName());
		return new ModelAndView("redirect:/serviceCenter/faq");
			
	}
	// View : 글읽기
	@GetMapping("/serviceCenter/faq/read")
	public ModelAndView faqRead(@NonNull Integer bno, Principal principal) {
		String username = principal == null? null : principal.getName();
		FaqBoardDto.Read read = service.read(bno,username);
		return new ModelAndView("main").addObject("viewname","serviceCenter/service/read.jsp").addObject("board", read);
	}
	// Post : 글삭제
	@Secured("ROLE_USER")
	@PostMapping("/serviceCenter/faq/delete")
	public ModelAndView delete(@NonNull Integer bno, Principal principal) {
		service.delete(bno,principal.getName());
		return new ModelAndView("redirect:/serviceCenter/faq");
	}
	// Post : 글변경
	@Secured("ROLE_USER")
	@PostMapping("/serviceCenter/faq/update")
	public ModelAndView faqDelete(@ModelAttribute @Valid FaqBoardDto.Update dto, BindingResult bindingResult, Principal principal) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.update(dto,principal.getName());
		return new ModelAndView("redirect:/serviceCenter/faq/read?bno="+dto.getBno());
	}
}
