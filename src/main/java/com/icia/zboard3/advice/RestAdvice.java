package com.icia.zboard3.advice;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.icia.zboard3.exception.*;

@ControllerAdvice(basePackages = {"com.icia.zboard3.controller.rest,com.icia.zboard3.service.rest"})
public class RestAdvice {
	
	private HttpHeaders getHeaders() {
	HttpHeaders httpheaders = new HttpHeaders();
	httpheaders.add("Content-Type","text/plain;charset=utf-8");
	return httpheaders;
	}
	@ExceptionHandler(UserJob.IdExistException.class)
	public ResponseEntity<Void> idExistExceptionHandler(){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
	@ExceptionHandler(UserJob.EmailExistException.class)
	public ResponseEntity<Void> emailExistExceptionHandler(){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
	@ExceptionHandler(UserJob.UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundExceptionHandler(){
		return new ResponseEntity<>("사용자를 찾을 수 없습니다", getHeaders(), HttpStatus.CONFLICT);
	}
	@ExceptionHandler(UserJob.FindPasswordFailException.class)
	public ResponseEntity<String> findPasswordFailException(){
		return new ResponseEntity<>("아이디 또는 이메일을 확인해주세요",getHeaders(),HttpStatus.CONFLICT);
	}
}
