import com.mysql.cj.protocol.Resultset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import javax.xml.transform.Result;

//��ǰ ��
public class DB2022TEAM01_ProductDetail {
	DB2022TEAM01_LogInDAO logInFunc = new DB2022TEAM01_LogInDAO();	//�α��� ���� Ȯ���� ���� ��ü
	//������ ���̽� ����
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


	public DB2022TEAM01_ProductDetail() {
		JFrame frame = new JFrame("��ǰ ��");
		Container contentPane = frame.getContentPane();
		
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
        JLabel label = new JLabel("��ǰ ��");        
        
        Font font = new Font("���� ���", Font.BOLD, 20);             
        
        //������ ����
        label.setBounds(453, 23, 94, 25);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        contentPane.add(label);
 
        //��ǰ �� ǥ�� �� attribute
        String col[] = { "��ǰ ID", "��ǰ��", "���̵� �׷�", "�����", "ī�װ�" , "�Ǹ���", "����", "��� ��¥" };   
        //ǥ
		DefaultTableModel model = new DefaultTableModel(col, 0);

		Connection conn = getConnection();

		String SQL = "select * from DB2022_product_list order by date desc;";
		try{
			ps = conn.prepareStatement(SQL);
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

				// ��� ��¥
				record.add(rs.getString("date"));
				model.addRow(record);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

        JTable table = new JTable(model);
        table.setRowHeight(30);	//���� ����
        
        table.setPreferredScrollableViewportSize(new Dimension (900, 650));
        table.setBackground(Color.pink); 
        
        JButton home = DB2022TEAM01_MyPage.make_home();	//Ȩ��ư
        home.setBounds(950, 5, 30, 30);
        contentPane.add(home);

        //��, �ż��ϱ� ���
        JLabel idInput_label = new JLabel("��ǰ ID:");
        JTextField idInput = new JTextField(10);	//��ǰ id �Է¶�
        JButton bt1 = new JButton("��");
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
        bt1.setBounds(567, 600, 45, 26);
        bt2.setBounds(621, 600, 60, 26);
        
        //�� ��ư ������ ��
        bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
				
				Long productId = Long.parseLong(idInput.getText());

				if(dao.isInWishlist(productId)){	//���ø���Ʈ ��� ���� - �̹� ���ø���Ʈ�� �ִ� ��ǰ�̾
					JOptionPane.showMessageDialog(null, "�̹� ���� ��ǰ�Դϴ�.", "���ø���Ʈ ��� ����", JOptionPane.ERROR_MESSAGE);
				}
				else if(!dao.isOkayAddWishlist(productId)){	//���ø���Ʈ ��� ���� - �����ڿ� �Ǹ��ڰ� ���Ƽ�
					JOptionPane.showMessageDialog(null, "������ ����� ��ǰ�Դϴ�.", "���ø���Ʈ ��� ����", JOptionPane.ERROR_MESSAGE);
				}
				else if (!dao.isInDetail(productId)){	//��ǰ �󼼿� ���� id�Է� -> ��� ����
					JOptionPane.showMessageDialog(null, "�������� �ʴ� id�Դϴ�.", "���ø���Ʈ ��� ����", JOptionPane.ERROR_MESSAGE);
				}
				else{
					dao.addWishlist(productId); // ���ø���Ʈ�� �߰���	
					new PopUp1();							
				}
				idInput.setText("");

			}
		});
        
        //���� ��ư ������ ��
        bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DB2022TEAM01_ProductDAO dao = new DB2022TEAM01_ProductDAO();
				Long productId = Long.parseLong(idInput.getText());
				if(!dao.isOkayBuying(productId)){	//���� ���� - �����ڿ� �Ǹ��ڰ� ���Ƽ�
					new PopUp4();
				}
				else if(!dao.isInDetail(productId)) {	//��ǰ �󼼿� ���� id�Է� -> ���� ����
					JOptionPane.showMessageDialog(null, "�������� �ʴ� id�Դϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else{	//���� ����
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
        
        //Ȩ��ư
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
        frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
        frame.setVisible(true); 
	}
}

class PopUp1 extends JFrame{	//���ø���Ʈ ��� ������ �˾�â
	public PopUp1() {	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame frame = new JFrame("�� �Ϸ�");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("���ø���Ʈ�� ��ǰ�� �߰��Ǿ����ϴ�", JLabel.CENTER);
		
		label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(label.getFont().deriveFont(15.0f));

	    panel.setLayout(new BorderLayout(10, 10));
		panel.add(label);
		frame.add(panel);
		
		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		frame.setVisible(true);
		
	}
}

class PopUp2 extends JFrame{	//���� �Ϸ�� �ߴ� �˾�â	
	public PopUp2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame frame = new JFrame("���� �Ϸ�");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("��ǰ ������ �Ϸ�Ǿ����ϴ�", JLabel.CENTER);
		
		label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(label.getFont().deriveFont(15.0f));

	    panel.setLayout(new BorderLayout(10, 10));
		panel.add(label);
		frame.add(panel);
		
		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		frame.setVisible(true);
		
	}
}




class PopUp3 extends JFrame{	//'�� ����' ��ư Ŭ������ �� �˾�â
	public PopUp3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame frame = new JFrame("�� ����");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("���ø���Ʈ���� �����߽��ϴ�.", JLabel.CENTER);
		
		label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(label.getFont().deriveFont(15.0f));

	    panel.setLayout(new BorderLayout(10, 10));
		panel.add(label);
		frame.add(panel);
		
		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		frame.setVisible(true);
		
	}
}

class PopUp4 extends JFrame{	//���� ���н� �ߴ� �˾�â
	public PopUp4() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame frame = new JFrame("���� ����");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("��ǰ ������ �����߽��ϴ�", JLabel.CENTER);

		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(15.0f));

		panel.setLayout(new BorderLayout(10, 10));
		panel.add(label);
		frame.add(panel);

		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		frame.setVisible(true);

	}
}
