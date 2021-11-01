package test01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {

	public static void main(String[] args) {
		//application Context 객체 생성
		ApplicationContext context 
			= new ClassPathXmlApplicationContext("test01/applicationContext.xml");
		//아이디로 지정했던 값을 넣을 수 있음
		//getBean에는 id로 지정했던 값을 넣어주면 됨. 의존성 주입
		Address addr = (Address) context.getBean("hong");
		System.out.println(addr);
		Address lee = context.getBean("lee", Address.class);
		System.out.println(lee);
		System.out.println(context.getBean("kim",Address.class));
		
	}
}
