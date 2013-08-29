package com.petitions.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.petitions.DummyLogin;

public class AuthorizationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpSession s = httpReq.getSession();
		HttpServletResponse httpRes = (HttpServletResponse) response;
		DummyLogin dummy = (DummyLogin) s.getAttribute("login");
		StringBuffer url = httpReq.getRequestURL();
		if (url.indexOf("login") > 0) {
			chain.doFilter(request, response);
		} else if (dummy != null && dummy.getLoginField().getRole() != null) {
			System.out.println(httpReq.getRequestURL());
			chain.doFilter(request, response);
		} else {
			httpRes.sendRedirect("/petitions/faces/login.xhtml");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
