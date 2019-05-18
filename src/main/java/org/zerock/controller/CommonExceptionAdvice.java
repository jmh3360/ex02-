package org.zerock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//컨트롤러에서 발생하는 Exception을  전문적으로 처리하는 클래스라는것을 명시
@ControllerAdvice
public class CommonExceptionAdvice {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	
//	화면에서 에러가 발생했을 때 자동으로 이리로 온다 세상신기하노..
//  ModelAndView 는 하나의 객체에 모델 데이터와 View의처리를 동시에 할 수 있는 객체임
//  과거의  스프링 MVC에서는 ModelAndView 타이을 이용해서 결과 데이터를 만들었지만 최근 스프링에서는 
//  굳이 그렇게 할 필요는 없고, 아래와 같이 지정된 파라미터를 사용하느 경우에 주로 사용한다.
	@ExceptionHandler(Exception.class)
	public ModelAndView errorModelAndView (Exception e) {
		
		logger.info(e.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error_common");
		modelAndView.addObject("exception", e);
		return modelAndView;
		
	}
}
