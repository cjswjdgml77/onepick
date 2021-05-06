package com.icia.zboard3.util;
import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebFilter("/*")
public class LoginMessageFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session= req.getSession();
		if(session.getAttribute("msg")!=null ) {
			request.setAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		chain.doFilter(request, response);
	}

}
