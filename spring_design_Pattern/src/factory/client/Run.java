package factory.client;

import factory.framework.ConnectorFactory;
import factory.framework.SMTPConnector;

public class Run {

	public static void main(String[] args) {
		//네이버 SMTP 서버에 연결을 해보자
		try {
			SMTPConnector conn = ConnectorFactory.builder()
					.url("smtp.naver.com")
					.id("ebcode2021@naver.com")
					.password("123456")
					.build()
					.getConnector();
			conn.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
