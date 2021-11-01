package strategy.framework;

import java.sql.Connection;

public interface ConnectionMaker {
	//사용자가 해야할 것을 강제해줌.
	Connection getConnection();
}
