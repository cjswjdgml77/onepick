package com.icia.zboard3.controller.mvc;

import java.security.*;
import java.util.*;

import javax.validation.*;

import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.icia.zboard3.dto.*;
import com.icia.zboard3.service.mvc.*;

import lombok.*;
import oracle.jdbc.proxy.annotation.*;
@RequiredArgsConstructor
@Controller
public class MemoController {
	private final MemoService service;
	//메모 쓰기 페이지 출력
	@GetMapping("/memo/write")
	public ModelAndView write() {
		return new ModelAndView("main").addObject("viewname","memo/write.jsp");
	}
	
	//메모 쓰기
	@PostMapping("/memo/write")
	public ModelAndView write(@Valid MemoDto.Write dto, BindingResult bindingResult, Principal principal) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.write(dto, principal.getName());
		return new ModelAndView("redirect:/memo/send");
	}
	//받은 메모함
	@GetMapping("/memo/receive")
	public ModelAndView receiver(Principal principal) {
		List<MemoDto.ListView> list = service.read(principal.getName());
		return new ModelAndView("main").addObject("viewname","memo/receive.jsp").addObject("memos",list);

	}
	//보낸 메모함
	@GetMapping("/memo/send")
	public ModelAndView send(Principal principal) {
		List<MemoDto.ListView> list = service.send(principal.getName());
		return new ModelAndView("main").addObject("viewname","memo/send.jsp").addObject("memos",list);
	}
	// 메모 읽기
	@GetMapping("/memo/read")
	public ModelAndView read(@NonNull Integer mno) {
		MemoDto.Read memo = service.read(mno);
		return new ModelAndView("main").addObject("viewname","memo/read.jsp").addObject("memo", memo);
	}
	// 보낸 메모 삭제
	@PostMapping("/memo/disabled_by_sender")
	public ModelAndView disabledBySender(@RequestParam List<Integer> mnos) {
		service.disabledBySender(mnos);
		return new ModelAndView("redirect:/memo/send");
	}
	// 받은 메모 삭제
	@PostMapping("/memo/disabled_by_receiver")
	public ModelAndView disabledByReceiver(@RequestParam List<Integer> mnos) {
		service.disabledByReceiver(mnos);
		return new ModelAndView("redirect:/memo/receive");
	}
}
