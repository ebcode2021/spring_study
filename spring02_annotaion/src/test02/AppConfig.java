package test02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//Spring bean 설정파일을 사용해하던 bean 등록을 자바 클래스에서 할수 있게끔 해주는 어노테이션
//Spring 3버전 이후로 제공된 기능
//@Bean : @Bean 어노테이션이 선언되어있는 메서드가 return 하는 객체를 bean으로 등록

@Configuration
public class AppConfig {
	
	//@Bean : 메서드명을 bean의 id로 해서 applicationContext에 등록
	@Bean
	public Book book() {
		return new Book("해리포터", "롤링", 80000);
	}
}
