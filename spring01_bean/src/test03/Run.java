package test03;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {
	public static void main(String[] args) {
		//ApplicationContext에 2021년 10월 9일 날짜를 가지는 java.util.Date빈을 등록하고
		//main 메서드에서 해당 빈을 호출해 toString 메서드를 호출하시오.
		//해당 bean의 아이디는 date
		ApplicationContext context = new ClassPathXmlApplicationContext("test03/applicationContext.xml");
		
		System.out.println("============");
		System.out.println(context.getBean("dateBean", Date.class));
		System.out.println(context.getBean("scoreBean", Score.class));
	}
}
