package com.icia.zboard3.security;

import static org.hamcrest.CoreMatchers.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.context.*;
import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.entity.User;

import lombok.*;
@RequiredArgsConstructor
@Component("loginFailureHandler")
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private final UserDao userDao;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String url = "/user/login";
		if(exception instanceof InternalAuthenticationServiceException)
			session.setAttribute("msg", "아이디를 확인하세요");
		else if(exception instanceof BadCredentialsException) {
			String username = request.getParameter("username");
			int loginFailureCnt = userDao.findById(username).get().getLoginFailureCnt()+1;
			if(loginFailureCnt<5) {
				userDao.update(User.builder().username(username).loginFailureCnt(loginFailureCnt).build());
				session.setAttribute("msg", "로그인 실패 횟수는" + loginFailureCnt+"입니다." +" 5회 실패시 계정이 블락됩니다.");
			}else {
				userDao.update(User.builder().username(username).enabled(false).build());
				session.setAttribute("msg", "로그인 실패 횟수 5회 이상이므로 계정을 블락됬습니다");
			}	
		}else if(exception instanceof DisabledException) {
			session.setAttribute("msg", "블락된 계정입니다. 관리자에게 문의 하십시오");
			url="/";
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, url);
	}
}
