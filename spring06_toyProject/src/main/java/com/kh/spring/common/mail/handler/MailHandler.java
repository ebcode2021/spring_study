package com.kh.spring.common.mail.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MailHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("mail")
	public String writeMailTemplate(@RequestParam Map<String,String> template) { //매개변수 명이 다르면 어노테이션
		logger.debug(template.toString());
		return "mail-template/" + template.get("mailTemplate");
		
	}
}
