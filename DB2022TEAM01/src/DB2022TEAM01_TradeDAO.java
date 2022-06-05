import java.sql.*;

public class DB2022TEAM01_TradeDAO {
	DB2022TEAM01_LogInDAO logInFunc;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team01";
    static final String USER = "DB2022Team01";
    static final String PASS = "DB2022Team01";

    private PreparedStatement ps = null; // 명령어
    private ResultSet rs = null;
    
    // mysql에 연결
    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Long tradeFind(){
        Connection conn = getConnection();

        Long id = Long.valueOf(0);
        String SQL = "select MAX(id) as max_id from DB2022_product";
        try{
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getLong("max_id");
                System.out.println("등록된 아이디 : " + id);
            }
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    // trade 등록
    public Boolean tradeRegister(){
        Connection conn = getConnection();

        String SQL = "insert into DB2022_trade(product_id) value (?);";

        try{
            ps = conn.prepareStatement(SQL);
            Long productId = tradeFind();
            System.out.println(productId);
            ps.setLong(1, productId);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
