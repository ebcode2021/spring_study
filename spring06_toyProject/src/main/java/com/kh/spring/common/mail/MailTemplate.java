package com.kh.spring.common.mail;

import java.util.HashMap;
import java.util.Map;

public class MailTemplate {

	private String templateName;
	private Map<String,String> component = new HashMap<>();
	
	public MailTemplate(String templateName) {//템플릿 이름을 지정
		this.templateName = templateName;
	}
	
	public void addMailComponent(String name, String comp) {
		component.put(name, comp);	
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public Map<String,String> getMailComp(){
		return component;
	}
	
	
}
