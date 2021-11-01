package templateCallback.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Stream;

import templateCallback.framework.ConnectionMaker;
import templateCallback.framework.MemberDao;

public class Run {
	//자바 람다
	//Functional Interface : 추상메서드가 하나만 존재하는 인터페이스
	//					   : 인터페이스 위에 @FunctionalInterface 어노테이션을 작성
	//Functional Interface의 추상메서드는 화살표함수 형태로 오버라이드가 가능
	
	// * 자바의 화살표함수도 매개변수의 타입을 생략 가능
	// * 화살표 함수의 메서드 body block을 생략할 경우, ; 도 생략
	// * 매개변수가 하나만 있는 경우 () 생략 가능
	// * return문 밖에 없는 경우, 메서드 body block과 return 생략
	// * 메서드 구문이 1줄인 경우, 메서드 body block을 생략
	
	public static void main(String[] args) {//파일이 쓸데없이 늘어나는 것을 막아줌
		//자바의 람다. 객체부르는 것도 지우고 메소드 선언부도 필요없으므로 매개변수에서 지워도 됨
		//익명클래스로 만들 인터페이스에 추상 메소드가 하나만 있어야됨
		//추상메소드가 2개면 익명함수가 뭘 가져와야할지 모르니까.
		
		String[] p = {"AA", "BB"};
		//Stream 문서 확인하기
		//FunctionalInterface를 찾으면, 고민할게 적어짐
		//매개변수의 타입도 크게 안봐도 되고, generic도 신경안써도되고.
		//이 친구 따라서 화살표 만들어주면 되겠구나 하고 구현해주면 됨
		Stream stream = Arrays.stream(p).filter(a -> {
			boolean res = a.equals("AA");
			System.out.println(a + " : " + res);
			return res;
		});
		
		System.out.println(Arrays.deepToString(stream.toArray()));
		
		String password = new MemberDao().selectPassword("DEV", () -> {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bm","1234");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;	
		});
		System.out.println("비밀번호는 " + password + "입니다.");

	}

}
