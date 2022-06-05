import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DB2022TEAM01_TradeList extends JFrame{
	DB2022TEAM01_LogInDAO logInFunc = new DB2022TEAM01_LogInDAO();

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
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

    private PreparedStatement ps;
    private ResultSet rs;

    public DB2022TEAM01_TradeList() {
        JFrame frame = new JFrame("거래 내역");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("거래 내역");
        JButton bt1 = new JButton("찜");
        JButton bt2 = new JButton("매수");

        Font font = new Font("맑은 고딕", Font.BOLD, 20);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);

        String col[] = { "번호","상품명", "아이돌 그룹", "멤버명", "카테고리", "매도자 ID", "매수자 ID", "가격"};

        DefaultTableModel model = new DefaultTableModel(col, 0);

       Connection conn = getConnection();

        String SQL = "select id, name, idol_id, gp, member, price, seller, category, buyer_id\n" +
                "from DB2022_product_withidol left outer join DB2022_trade\n" +
                "on id = DB2022_trade.product_id\n" +
                "where seller = ? or buyer_id = ?;";

        Long userId= logInFunc.getLogInUser();
        String username = logInFunc.getLogInUserName(userId);

        try{
            ps = conn.prepareStatement(SQL);
            ps.setString(1, username);
            ps.setLong(2, userId);
            rs = ps.executeQuery();

            while(rs.next()){
                Vector record = new Vector();
                record.add(rs.getLong("id"));
                record.add(rs.getString("name"));
                record.add(rs.getString("gp"));
                record.add(rs.getString("member"));
                record.add(rs.getString("category"));
                record.add(rs.getString("seller"));


                Long buyerId = rs.getLong("buyer_id");
                if(buyerId != null){
                    String  buyerName = logInFunc.getLogInUserName(buyerId);
                    record.add(buyerName);
                }
                else{
                    record.add(" ");
                }

                record.add(rs.getLong("price"));

                model.addRow(record);
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        JTable table = new JTable(model);
        table.setRowHeight(30);

        table.setPreferredScrollableViewportSize(new Dimension (950, 650));
        table.setBackground(Color.pink);

        JButton home = DB2022TEAM01_MyPage.make_home();
        home.setBounds(950, 5, 30, 30);
        panel.add(home);

        //홈버튼
        home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.dispose();
                new DB2022TEAM01_MyPage();
            }
        });

        JScrollPane pane = new JScrollPane(table);
        panel.setLayout(new BorderLayout(10, 10));
        panel.add(pane, BorderLayout.CENTER);
        panel.add(label, BorderLayout.NORTH);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);	//화면 중앙에 뜸
        frame.setVisible(true);

    }
}
