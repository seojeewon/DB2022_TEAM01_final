import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DB2022TEAM01_WishList extends JFrame{
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

    public DB2022TEAM01_WishList() {
        JFrame frame = new JFrame("���ø���Ʈ");
        Container contentPane = frame.getContentPane();
        
        contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
        JLabel label = new JLabel("���ø���Ʈ");        

        Font font = new Font("���� ���", Font.BOLD, 20);

        //������ ����
        label.setBounds(445, 23, 110, 25);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        contentPane.add(label);

        String col[] = { "��ǰ ID", "��ǰ��", "���̵� �׷�", "�����", "ī�װ�", "�Ǹ���", "����" };

        DefaultTableModel model = new DefaultTableModel(col, 0);
        Long userId = loginfunc.getLogInUser();


        Connection conn = getConnection();

        String SQL = "select id, name, gp, member, category, seller, price, date\n" +
                "from DB2022_product, DB2022_wishlist, DB2022_idol\n" +
                "where DB2022_wishlist.user_id = ? and DB2022_wishlist.product_id = DB2022_product.id and DB2022_product.idol_id = DB2022_idol.idol_id " +
                "order by date;\n";
        try{
            ps = conn.prepareStatement(SQL);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            int row = 1;
            while(rs.next()){
                Vector record = new Vector();
                row++;
                //��ǰ id
				record.add(rs.getInt("id"));
				
                // ��ǰ��
                record.add(rs.getString("name"));

                // ���̵� �׷�
                record.add(rs.getString("gp"));

                // ���̵� �̸�
                record.add(rs.getString("member"));

                // ī�װ�
                record.add(rs.getString("category"));

                // �Ǹ���
                record.add(rs.getString("seller"));

                // ����
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
        
        //Ȩ��ư �߰�
        JButton home = DB2022TEAM01_MyPage.make_home();
        home.setBounds(950, 5, 30, 30);
        contentPane.add(home);

        //�� ����, �ż��ϱ�
        JLabel idInput_label = new JLabel("��ǰ ID:");
        JTextField idInput = new JTextField(10);
        JButton bt1 = new JButton("�� ����");
        JButton bt2 = new JButton("����");
        
        Font font2 = new Font("���� ���", Font.BOLD, 15);
        Font font3 = new Font("���� ���", Font.BOLD, 11);
        Color btn_color=new Color(0xFF6472);
		
        idInput_label.setFont(font2);
        bt1.setFont(font3);
        bt1.setBackground(btn_color);
		bt1.setForeground(Color.white);
        bt2.setFont(font3);
        bt2.setBackground(btn_color);
		bt2.setForeground(Color.white);
        
        idInput_label.setBounds(298, 600, 58, 20);
        idInput.setBounds(369, 600, 178, 26);
        bt1.setBounds(567, 600, 75, 26);
        bt2.setBounds(653, 600, 60, 26);
        
        contentPane.add(idInput_label);
        contentPane.add(idInput);
        contentPane.add(bt1);
        contentPane.add(bt2);
        
                
        //Ȩ��ư
        home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.dispose();
                new DB2022TEAM01_MyPage();
            }
        });
        //�� ���� ��ư
        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long productId = Long.parseLong(idInput.getText());
                DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
                dao.deleteWishList(productId);	//���ø���Ʈ���� ����
                new PopUp3();
            }
        });
        //���� ��ư
        bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long productId = Long.parseLong(idInput.getText());
                DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
                dao.deleteWishList(productId);
                dao.buyProduct(productId);
                new PopUp2();
            }
        });

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 60, 988, 510);
        contentPane.add(pane);        

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
        frame.setVisible(true);


    }
}
