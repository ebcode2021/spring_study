package templateMethod.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//템플릿 디자인 패턴
import templateMethod.framework.dao.AbstractMemberDao;
//추상클래스가 상속되었기 때문에, 다른 기능을 추가하기 힘들다.(템플릿 메소드)
// -> 
//인터페이스를 사용해서 다중상속을 한다. 
//추상클래스가 아니라 인터페이스를 사용(Strategy Pattern)
public class MemberDao extends AbstractMemberDao{

	@Override
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bm","1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
