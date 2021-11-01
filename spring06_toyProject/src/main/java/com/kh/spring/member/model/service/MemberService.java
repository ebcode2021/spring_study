package com.kh.spring.member.model.service;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.common.code.Config;
import com.kh.spring.common.mail.MailSender;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.repository.MemberRepository;
import com.kh.spring.member.validator.JoinForm;

public interface MemberService {	
	
	void insertMember(JoinForm form);
	

	Member authenticateUser(Member member);

	Member selectMemberByUserId(String userId);

	void authenticateByEmail(JoinForm form, String token);
}
