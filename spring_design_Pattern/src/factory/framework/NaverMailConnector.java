package factory.framework;

import java.util.Date;

public class NaverMailConnector extends SMTPConnector{

	protected NaverMailConnector(String url, String id, String password, Date connectTime) {
		super(url, id, password, connectTime);
	}

	@Override
	public void connect() {
		System.out.println("Naver SMTP 서버에 연결 성공했습니다.");
	}
	

}
