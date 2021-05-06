package com.icia.zboard3.util;

import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.servlet.handler.*;
@Component
public class AnonymousInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal().toString().equals("anonymousUser")==false)
			throw new IllegalAccessException();
		return true;	
	}
}
