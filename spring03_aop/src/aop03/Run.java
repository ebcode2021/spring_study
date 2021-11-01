package aop03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {
//Proxy 객체 만드는 방법 1.인터페이스 사용 2.인터페이스가 없으면 byte 코드를 조작해서 proxy 객체 만들기
	public static void main(String[] args) {
		ApplicationContext context = 
					new ClassPathXmlApplicationContext("aop03/applicationContext.xml");
		
		Man man = context.getBean("man", Man.class); //Aop 타입이 되었을 때만 프록시 객체로 넘어옴.
		Woman woman = context.getBean("woman", Woman.class); //Aop 타입이 아닐 경우에는 Woman woman 가능
		
		
		woman.develop();
		System.out.println("====================");
		man.develop();
		System.out.println("====================");
		man.playGame();
	}

}
