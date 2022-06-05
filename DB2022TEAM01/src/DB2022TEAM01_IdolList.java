import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DB2022TEAM01_IdolList {
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

    DB2022TEAM01_LogInDAO loginfunc = new DB2022TEAM01_LogInDAO();
    DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
    
    private PreparedStatement ps;
    private ResultSet rs;
	
	public DB2022TEAM01_IdolList() {		
		
		JFrame frame = new JFrame("아이돌 목록");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("아이돌 목록");
        
        Font font = new Font("맑은 고딕", Font.BOLD, 20);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);

        String col[] = { "아이돌 그룹", "멤버명" };

        DefaultTableModel model = new DefaultTableModel(col, 0);

       Connection conn = getConnection();

        String SQL = "select *" +
                "from DB2022_idol_list;";

        try{
            ps = conn.prepareStatement(SQL);            
            rs = ps.executeQuery();

            while(rs.next()){
                Vector record = new Vector();
                
                record.add(rs.getString("gp"));
                record.add(rs.getString("member"));             

                model.addRow(record);
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        JTable table = new JTable(model);
        table.setRowHeight(30);

        table.setPreferredScrollableViewportSize(new Dimension (950, 650));
        table.setBackground(new Color(0xFFEBCD));

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
