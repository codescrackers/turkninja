package com.yazilimokulu.mvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yazilimokulu.mvc.HitCounter;

public class GlobalInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private HitCounter counter;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		counter.setHits(counter.getHits() + 1);
		System.out.println("Hits: " + counter.getHits());
		return super.preHandle(request, response, handler);
	}

	
}
