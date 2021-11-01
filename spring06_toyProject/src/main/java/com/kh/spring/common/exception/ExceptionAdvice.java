package com.kh.spring.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice(basePackages = "com.kh.spring") //여러 곳에서 공통적으로 필요한 예외처리. 기능들을 모듈화 할 수 있음 com.kh.toy 아래에 있는 모든 컨트롤러를 대상
public class ExceptionAdvice {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//예외가 발생했으므로 응답상태코드를 500번으로 지정. default가 200.
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HandlableException.class)
	public String handlableExceptionProcess(HandlableException e, Model model) {
		model.addAttribute("msg", e.error.MESSAGE);
		model.addAttribute("url", e.error.URL);
		return "error/result";
	}
	
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionProcess(DataAccessException e, Model model) {
		logger.error(e.getMessage());
		model.addAttribute("msg", "데이터베이스 접근 도중 예외가 발생하였습니다.");
		model.addAttribute("url", "/");
		return "error/result";
	}
}
