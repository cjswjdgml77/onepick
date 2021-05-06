package com.icia.zboard3.security;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;
import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.entity.*;

import lombok.*;
@RequiredArgsConstructor
@Component("loginSuccessHandler")
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	private final UserDao userDao;
	@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
		String username = authentication.getName();
		User user = User.builder().username(username).loginFailureCnt(0).build();
		userDao.update(user);
		
		SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		RedirectStrategy rs = new DefaultRedirectStrategy();
		if(savedRequest != null)
			rs.sendRedirect(request, response, savedRequest.getRedirectUrl());
		else
			rs.sendRedirect(request, response, "/");
	}
	
}
