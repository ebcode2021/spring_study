package templateCallback.framework;

import java.sql.Connection;

//다른 사람들이 이걸 화살표 함수로 쓰기 위해서 만들었다는 것을 알게 됨.
//실수로라도 2개 이상의 메소드를 만드는 것을 막아주는 역할
@FunctionalInterface 
public interface ConnectionMaker {
	//사용자가 해야할 것을 강제해줌
	Connection getConnection();
	
	//추상메소드가 아닌 메소드는 상관없음
	default void test() {
		System.out.println();
	}
	
	public static String pp() {
		return "";
	}
}
