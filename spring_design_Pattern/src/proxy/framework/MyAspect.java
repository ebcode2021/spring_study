package proxy.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import proxy.client.Man;
import proxy.client.Woman;

public class MyAspect implements Developer{

	private Developer developer;
	
	//자바 Reflection : 객체 또는 클래스명을 통해 타입에 대한 정보를 조사할 수 있는 기법
	
	
	public MyAspect(String className) {
		
		try {
			developer = (Developer) Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		switch (className) {
		case "proxy.Man":
			developer= new Man();
			break;
		case "proxy.Woman":
			developer= new Woman();
			break;
		default:
			throw new RuntimeException("잘못된 클래스명을 넣었습니다.");
		
		}
		*/
	}
	
	@Override
	public void develop() {
		System.out.println("출근 카드를 찍는다.");

		try {
			developer.develop();
			
			/*
			 * System.out.println(developer.getClass()); Method[] methods =
			 * developer.getClass().getDeclaredMethods(); for(Method method : methods) {
			 * System.out.println(method.getName() + " 호출!"); method.invoke(developer);
			 * //System.out.println(method.getName()); }
			 * System.out.println("----------------------");
			 * //developer.getClass().getMethod("play").invoke(developer); Method play =
			 * developer.getClass().getDeclaredMethod("play"); // getDeclaredMethod를 해야
			 * private 메서드도 가져옴 //private인데 외부에서 호출 가능. 근데 이거 거의 안씀.자바 근간을 흔들어서. 대신 필요한 시점에
			 * 잘 써야함..우리는 쓸 필요 없음 play.setAccessible(true); play.invoke(developer);
			 * developer.develop();
			 */
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("쉬는 날이었다.");
		} finally {
			System.out.println("집에 간다.");
		}
	}
		
	

	
}
