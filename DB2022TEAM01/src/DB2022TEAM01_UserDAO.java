import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB2022TEAM01_UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	DB2022TEAM01_LogInDAO logInFunc = new DB2022TEAM01_LogInDAO();


	public DB2022TEAM01_UserDAO() {
		try {			
			String dbURL = DB2022TEAM01_ProductDAO.DB_URL;
			String dbID = DB2022TEAM01_ProductDAO.USER;
			String dbPW = DB2022TEAM01_ProductDAO.PASS;
			Class.forName(DB2022TEAM01_ProductDAO.JDBC_DRIVER);
			conn=DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
		}
	}



	//실제로 로그인하는 함수.
	public int login(String userID, String userPassword) {
		String SQL = "SELECT id, password FROM DB2022_user WHERE name = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);


			rs = pstmt.executeQuery();	//rs에 쿼리문 실행 결과 넣기
			if(rs.next()) {	//아이디 존재
				if(rs.getString("password").contentEquals(userPassword)) {
					logInFunc.convertUserLogIn(rs.getLong("id"));
					return 1;	//로그인 성공
				}
				else {
					return 0;	//비밀번호 틀림
				}
			}
			//아이디 없음
			return -1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return -2;	//DB 오류
	}
	
	//가입 함수
	public boolean signUp(String userID, String userPassword) {
		boolean approval = false;
		String SQL = "INSERT INTO DB2022_user(name, password) VALUES(?,?)";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			int r = pstmt.executeUpdate();
			if(r > 0){
                System.out.println("가입 성공");
				conn.commit();
				pstmt.close();
                approval = true;
            }else{
                System.out.println("가입 실패");
            }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return approval;
	}
}
