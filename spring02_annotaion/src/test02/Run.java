package test02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {

	public static void main(String[] args) {
		ApplicationContext context = 
					new ClassPathXmlApplicationContext("test02/applicationContext.xml");
		
		//컴포넌트. autowired.
		Rent rent = context.getBean("rent", Rent.class);
		
		System.out.println(rent);
		
		//@Bean으로 등록
		Book book = context.getBean("book", Book.class);
		System.out.println(book);

	}

}
