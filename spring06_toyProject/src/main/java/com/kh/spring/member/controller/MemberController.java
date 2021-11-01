package com.kh.spring.member.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandlableException;
import com.kh.spring.common.validator.ValidateResult;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.validator.JoinForm;
import com.kh.spring.member.validator.JoinFormValidator;

import lombok.RequiredArgsConstructor;

/*
 * 스프링 프레임워크에서 쓰이는 어노테이션 정리
 * 1. @Controller : 해당 클래스를 applicationContext에 bean으로 등록
 * 					Controller와 관련된 annotation을 사용할 수 있게 해준다.
 * 2. @RequestMapping : 요청 URL과 Controller의 메서드 매핑을 지원
 * 					클래스 위에 선언할 경우, 해당 클래스의 모든 메서드가 지정된 경로를 상위경로로 갖는다.
 * 3. @GetMapping : Get 방식 요청 URL과 Controler의 메서드 매핑을 지원
 * 4. @PostMapping : Post 방식의 요청 URL과 Controller의 메서드 매핑을 지원
 * 5. @RequestParam : 요청 파라미터를 컨트롤러 메서드의 매개변수에 바인드
 * 					  단, Context-type이 application/x-www-form-urlEncoded인 경우에만 가능
 * 					  FormHttpMessageConverter가 동작
 * 6. @ModelAttribute : 요청 파라미터를 setter를 사용해 메서드 매개변수에 선언된 객체에 바인드
 * 					  Model 객체의 attribute로 자동으로 저장
 * 7. @RequestBody : 요청 body를 읽어서 자바의 객체에 바인드
 * 					 application/x-www-form-urlEncoded를 지원하지 않는다. (비동기통신)
 * 8. @RequestHeader : 요청 헤더를 메서드의 매개변수에 바인드
 * 9. @SessionAttribute : 원하는 session의 속성값을 매개변수에 바인드
 * 10. @CookieValue : 원하는 cookie 값을 매개변수에 바인드
 * 11. @PathVariable : url 템플릿에 담긴 파라미터값을 매개변수에 바인드
 * 12. @ResponseBody : 메서드가 반환하는 값을 응답 body에 작성
 * 13. Servlet 객체를 컨트롤러의 매개변수에 선언해 주입받을 수 있다.
 * HttpServletRequest, HttpServletResponse, HttpSession
 * 
 * 
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final MemberService memberService;
	private final JoinFormValidator joinFormValidator;
	
	/*
	 * Model 속성명 자동 생성 규칙
	 *  com.myapp.Product becomes "product"
		com.myapp.MyProduct becomes "myProduct"
		com.myapp.UKProduct becomes "UKProduct"

	 */


	
	
	@InitBinder(value="joinForm") //model의속성 중 속성명이 joinForm인 속성이 있는 경우 initBinder 메서드 실행
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValidator);
	}

	@GetMapping("join")
	public void joinForm(Model model) {
		model.addAttribute(new JoinForm()).addAttribute("error", new ValidateResult().getError());
		//throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		
	};
	
	@PostMapping("join")
	public String join(@Validated JoinForm form, Errors errors,/*반드시 검증될 객체 뒤에 작성*/ Model model, HttpSession session,RedirectAttributes redirectAttr) { //@RequestParam 생략 가능 @ModelAttribute도 생략 가능
		
		logger.debug(session.getId());
		
		ValidateResult vr = new ValidateResult();
		model.addAttribute(vr.getError());
		
		if(errors.hasErrors()) {
			vr.addError(errors);
			return "member/join";
		}
		
		//token 생성
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistUser", form);
		session.setAttribute("persistToken", token);
		
		memberService.authenticateByEmail(form,token);
		
		redirectAttr.addFlashAttribute("message", "이메일이 발송!");
		
		return "redirect:/";
	}; 
	
	@GetMapping("join-impl/{token}")
	public String joinImpl(@PathVariable String token
				,@SessionAttribute(value="persistToken", required=false) String persistToken
				,@SessionAttribute(value="persistUser", required=false) JoinForm form
				,RedirectAttributes redirectAttrs
				,HttpSession session) { // getMapping 뒤에 token 가져오는걸 @Path 뒤에서 가져와줌
		
		logger.debug(session.getId());
		logger.debug(persistToken);
		logger.debug(token);
		
		if(!token.equals(persistToken)) {
			throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		}
		
		
		memberService.insertMember(form);
		redirectAttrs.addFlashAttribute("message", "회원가입을 환영합니다^^");
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		return "redirect:/member/login";
	}
	
	
	@PostMapping("join-json")
	public String joinWithJson(@RequestBody Member member) {
		logger.debug(member.toString());
		return "index";
	}
	
	//로그인 페이지 이동 메서드
	@GetMapping("login") //페이지 이동은 get, 요청은 post
	public void login() {};
	
	//로그인 실행 메서드
	//재지정할 jsp : mypage
	@PostMapping("login")
	public String loginImpl(Member member, HttpSession session,RedirectAttributes redirctAttr) {
		
		Member certifiedUser = memberService.authenticateUser(member);
		
		if(certifiedUser==null) {
			redirctAttr.addFlashAttribute("message","아이디나 패스워드가 정확하지 않습니다.");
			return "redirect:/member/login";
		}
		
		session.setAttribute("authentication", certifiedUser);
		logger.debug(certifiedUser.toString());
		return "redirect:/member/mypage";
	}
	
	@GetMapping("mypage") 
	public String mypage(@CookieValue(name="JSESSIONID", required=false) String sessionId,
						@SessionAttribute(name="authentication", required=false) Member member,
						HttpServletResponse response) {
		//Cookie 생성 및 응답헤더에 추가
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName("testCookie"); 
		cookieGenerator.addCookie(response, "testcookie"); //쿠키에는 공백 X
		
		logger.debug("@CookieValue : " + sessionId);
		logger.debug("@SessionAttribute : " + member); 
		
		return "member/mypage";
		
	}
	
	@GetMapping("id-check")
	@ResponseBody //ResponseBody가 없을때는 view의 경로. ResponseBody가 있으면 ResponseBody에 작성된 값
	public String idCheck(String userId) {
		Member member = memberService.selectMemberByUserId(userId);
		
		if(member==null) { //원래는 return "available" return "disable"이였는데 null,member로 하니까 json 파싱이 찍히
			return "available";
		}else {
			return "disable";
		}
		
	}
	
	
	
	
	
}
