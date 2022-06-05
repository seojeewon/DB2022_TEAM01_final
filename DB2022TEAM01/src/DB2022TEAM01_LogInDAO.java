import java.sql.*;

//유저의 로그인 상태를 관리하는 클래스
public class DB2022TEAM01_LogInDAO {
	//데이터베이스 연결
    private PreparedStatement ps;
    private ResultSet rs;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team01";
    static final String USER = "DB2022Team01";
    static final String PASS = "DB2022Team01";

    public Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    // 로그인 페이지에서 로그인 성공 시 login 컬럼의 값을 true로 바꾼다
    public boolean convertUserLogIn(Long userID){
        Connection conn = getConnection();
        String SQL = "update DB2022_user set login = true where id = ?";	//update
        try{
            ps = conn.prepareStatement(SQL);
            ps.setLong(1, userID);
            ps.executeUpdate();
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // 로그인한 유저의 id 번호를 가져온다
    public Long getLogInUser(){
        Connection conn = getConnection();
        Long userId = Long.valueOf(0);
        String SQL = "select * from DB2022_user use index(idx_user_login) where login = 1";	//index 사용
        try{
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            if(rs.next()){
                userId = rs.getLong("id");
                System.out.print(userId);
            }
            return userId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Long.valueOf(0);
    }

    // 로그인한 유저의 name을 가져온다
    public String getLogInUserName(Long userId){
        String userName = " ";
        Connection conn = getConnection();
        String SQL = "select name from DB2022_user use index(idx_user) where id = ?";
        try{
            ps = conn.prepareStatement(SQL);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if(rs.next()){
                userName = rs.getString("name");
            }
            return userName;

        }catch (Exception e){
            e.printStackTrace();
        }
        return "실패";
    }

    // 처음 파일을 시작할 때, 로그인 상태를 초기화한다
    public Boolean Start(){
        Connection conn = getConnection();
        String SQL = "update DB2022_user set login = false where login = true";
        try{
            ps = conn.prepareStatement(SQL);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
