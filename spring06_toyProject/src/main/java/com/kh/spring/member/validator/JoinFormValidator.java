package com.kh.spring.member.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.member.model.repository.MemberRepository;


@Component
public class JoinFormValidator implements Validator {
	
	private final MemberRepository memberRepository;
	
	public JoinFormValidator(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		//JoinFormValidator의 타입 검증. joinForm일때만 이게 돈다.
		return JoinForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JoinForm form = (JoinForm) target;
		
		//1. 아이디 존재 유무
		if(memberRepository.selectMemberByUserId(form.getUserId())!=null) {
			errors.rejectValue("userId", "error-userId", "이미 존재하는 아이디입니다.");
		}
		//2. 비밀번호 8글자 이상, 숫자 영문자 특수문자 조합인지 확인
		boolean valid = Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", form.getPassword());
		if (!valid) {
			errors.rejectValue("password", "error-password", "비밀번호는 8글자 이상의 숫자 영문자 특수문자 조합입니다. ");
		}
		//3. 휴대폰은 9-11자리의 숫자
		valid = Pattern.matches("^\\d{9,11}$", form.getTell());
		if (!valid) {
			errors.rejectValue("tell", "error-tell", "전화번호는 9-11자리의 숫자입니다.");
		}
	}

}
