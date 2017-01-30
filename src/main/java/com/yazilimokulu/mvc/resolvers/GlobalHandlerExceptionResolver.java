package com.yazilimokulu.mvc.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {
	
	final static Logger logger = Logger.getLogger(GlobalHandlerExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		logger.info(exception);
		ModelAndView mav = new ModelAndView();
		if(exception.getClass().getSimpleName().equals("AccessDeniedException")){
			mav.setViewName("403");
			return mav;
		}
		mav.setViewName("global_error");
		return mav;
	}

}
