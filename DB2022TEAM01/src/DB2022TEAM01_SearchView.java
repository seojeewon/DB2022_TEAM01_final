import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DB2022TEAM01_SearchView {
	//데이터베이스 연결
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

    public DB2022TEAM01_SearchView(String gp, String member, String keyword, String category){
        JFrame frame = new JFrame("검색 결과");
        Container contentPane = frame.getContentPane();

        contentPane.setBackground(Color.white);
        contentPane.setLayout(null);

        JLabel label = new JLabel("검색 결과");

        Font font = new Font("맑은 고딕", Font.BOLD, 20);

        //페이지 제목
        label.setBounds(445, 23, 110, 25);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        contentPane.add(label);

        String col[] = { "상품 ID", "상품명", "아이돌 그룹", "멤버명", "카테고리", "가격" };

        DefaultTableModel model = new DefaultTableModel(col, 0);
        Long userId = loginfunc.getLogInUser();



        Connection conn = getConnection();


        try{
            String SQL = "select id, name, gp, member, category, price, isSold from DB2022_product use index(idx_category), (\n" +
                    "select gp, member from DB2022_idol\n" +
                    "where idol_id = ?) idol\n" +
                    "where  DB2022_product.idol_id = ?\n" +
                    "and category = ? and name like ?\n" +
                    "and isSold = false\n" +
                    "order by date";


            ps = conn.prepareStatement(SQL);
            DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
            Long idolId = dao.FindIdol(gp, member);
            ps.setLong(1, idolId);
            ps.setLong(2, idolId);
            ps.setString(3, category);

            String searchInput = "%" + keyword + "%";
            ps.setString(4, searchInput);

            rs = ps.executeQuery();
            int row = 1;
            while(rs.next()){
                Vector record = new Vector();
                row++;
                //상품 id
                record.add(rs.getInt("id"));

                // 샹품명
                record.add(rs.getString("name"));

                // 아이돌 그룹
                record.add(rs.getString("gp"));

                // 아이돌 이름
                record.add(rs.getString("member"));

                // 카테고리
                record.add(rs.getString("category"));

                // 가격
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
        
        //찜, 구매하기 기능
        JLabel idInput_label = new JLabel("상품 ID:");
        JTextField idInput = new JTextField(10);
        JButton bt1 = new JButton("찜");
        JButton bt2 = new JButton("구매");
        
        Font font2 = new Font("맑은 고딕", Font.BOLD, 15);
        Font font3 = new Font("맑은 고딕", Font.BOLD, 11);
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
        bt1.setBounds(567, 600, 45, 26);
        bt2.setBounds(621, 600, 60, 26);
        
        //찜 버튼 눌렀을 때 - 위시리스트에 추가
        bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
				Long productId = Long.parseLong(idInput.getText());
				
				if(dao.isInWishlist(productId)){	//위시리스트 등록 실패 - 이미 위시리스트에 있는 상품이어서
					JOptionPane.showMessageDialog(null, "이미 찜한 상품입니다.", "위시리스트 등록 실패", JOptionPane.ERROR_MESSAGE);
				}
				else if(!dao.isOkayAddWishlist(productId)){	//위시리스트 등록 실패 - 구매자와 판매자가 같아서
					JOptionPane.showMessageDialog(null, "본인이 등록한 상품입니다.", "위시리스트 등록 실패", JOptionPane.ERROR_MESSAGE);
				}
				else if(!dao.isInDetail(productId)) {	//상품상세에 없는 id입력 -> 추가 실패
					JOptionPane.showMessageDialog(null, "존재하지 않는 id입니다.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					dao.addWishlist(productId);
					new PopUp1();
				}			
				idInput.setText("");
			}
		});
        
        //구매 버튼 눌렀을 때
        bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
				Long productId = Long.parseLong(idInput.getText());
				if(!dao.isOkayBuying(productId)){	//구입 실패 - 구매자와 판매자가 같아서
					new PopUp4();
				}
				else if(!dao.isInDetail(productId)) {	//상품상세에 없는 id입력 -> 추가 실패
					JOptionPane.showMessageDialog(null, "존재하지 않는 id입니다.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					dao.buyProduct(productId);
					new PopUp2();
				}
				idInput.setText("");
			
			}
		});
        
        contentPane.add(idInput_label);
        contentPane.add(idInput);
        contentPane.add(bt1);
        contentPane.add(bt2);

        //홈버튼 추가
        JButton home = DB2022TEAM01_MyPage.make_home();
        home.setBounds(950, 5, 30, 30);
        contentPane.add(home);


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
        pane.setBounds(0, 60, 988, 510);
        contentPane.add(pane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);	//화면 중앙에 뜸
        frame.setVisible(true);

    }
}
