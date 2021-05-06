package com.icia.zboard3.controller.rest;


import java.security.*;

import javax.validation.*;

import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.icia.zboard3.dto.*;
import com.icia.zboard3.service.rest.*;

import lombok.*;
import oracle.jdbc.proxy.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserRestController {
	private final UserRestService service;
	
	//아이디 중복 확인
	@GetMapping("/users/user/username")
	public ResponseEntity<?> idAvailableCheck(@RequestParam String username){
		service.idAvailableCheck(username);
		return ResponseEntity.ok(null);
	}
	//이메일 중복 확인
	@GetMapping("/users/user/email")
	public ResponseEntity<?> emailAvailableCheck(@RequestParam String email){
		service.emailAvailableCheck(email);
		return ResponseEntity.ok(null);
	}
	//회원가입
	@PostMapping("/users/new")
	public ResponseEntity<?> join(@ModelAttribute @Valid UserDto.Join dto, BindingResult bindingResult) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.join(dto);
		return ResponseEntity.ok(null);
	}
	// 아이디 찾기
	@GetMapping("/users/username/email")
	public ResponseEntity<?> findUsername(@RequestParam String email){
		String username = service.findUsername(email);
		return ResponseEntity.ok(username);
	}
	// 비밀번호 찾기
	@GetMapping("/users/username/password")
	public ResponseEntity<?> resetPassword(@ModelAttribute @Valid UserDto.ResetPassword dto, BindingResult bindingResult) throws BindException{
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.findPassword(dto);
		return ResponseEntity.ok(null);
	}
	//이름변경
	@Secured("ROLE_USER")
	@PatchMapping("/users/username")
	public ResponseEntity<?> changeIrum(@RequestParam String irum, Principal principal){
		service.changeIrum(irum,principal.getName());
		return ResponseEntity.ok(null);
	}
	//비밀번호변경
	@Secured("ROLE_USER")
	@PatchMapping("/users/password")
	public ResponseEntity<?> changePwd(@Valid UserDto.ChangePassword dto, BindingResult bindingResult, Principal principal) throws BindException{
		System.out.println("================================="+dto);
		if(bindingResult.hasErrors())	
			throw new BindException(bindingResult);
		service.changePwd(dto,principal.getName());
		return ResponseEntity.ok(null);
	}
	//전체변경
	@Secured("ROLE_USER")
	@PostMapping("/users/alter")
	public ResponseEntity<Void> update(UserDto.Update dto, BindingResult bindingResult, 
			@RequestParam(required = false) MultipartFile profile, Principal principal) throws BindException {
	System.out.println("============================================");	
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.update(dto, profile, principal.getName());
		return ResponseEntity.ok(null);
	}
	//가입일 조회
	@GetMapping("/users/joinday")
	public ResponseEntity<?> joinday(@NonNull String username){
		String joinday = service.joinday(username);
		return ResponseEntity.ok(joinday);
	}
}
