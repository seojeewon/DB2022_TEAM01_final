
public class DB2022TEAM01_Main {

	public static void main(String[] args) {
		DB2022TEAM01_LogInDAO logInFunc = new DB2022TEAM01_LogInDAO();
        logInFunc.Start();	//로그인 상태 초기화
        
        new DB2022TEAM01_LogIn();	//로그인 창

	}

}
