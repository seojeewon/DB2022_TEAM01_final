import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DB2022TEAM01_MyPage extends JFrame{
	public DB2022TEAM01_MyPage() {
		setTitle("Ȩ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
		//user ����
		DB2022TEAM01_LogInDAO userInfo = new DB2022TEAM01_LogInDAO();
		Long user_id = userInfo.getLogInUser();
		String user_name = userInfo.getLogInUserName(user_id);
		JLabel userName = new JLabel(user_name+"��");
		userName.setHorizontalAlignment(JLabel.RIGHT);
		Font font_u = new Font("���� ���", Font.BOLD, 20);
		userName.setFont(font_u);
		userName.setBounds(745, 23, 221, 30);
		
		// ��ư ����
	    JButton btn1 = new JButton("��ǰ���");
	    JButton btn2 = new JButton("��ǰ��");
	    JButton btn3 = new JButton("�˻�");
 	    JButton btn4 = new JButton("����������-���ø���Ʈ");
	    JButton btn5 = new JButton("����������-�ŷ�����");
	    JButton btn6 = new JButton("���̵� ����Ʈ");
	    
	    //��ư�� ����-����
	    Color btn_color=new Color(0xFF6472);
	    Font font = new Font("���� ���", Font.BOLD, 20);
	    
	    btn1.setBackground(btn_color);
	    btn1.setForeground(Color.white);
	    btn1.setFont(font);
	    
	    btn2.setBackground(btn_color);
	    btn2.setForeground(Color.white);
	    btn2.setFont(font);
	    
	    btn3.setBackground(btn_color);
	    btn3.setForeground(Color.white);
	    btn3.setFont(font);
	    
	    btn4.setBackground(btn_color);
	    btn4.setForeground(Color.white);
	    btn4.setFont(font);
	    
	    btn5.setBackground(btn_color);
	    btn5.setForeground(Color.white);
	    btn5.setFont(font);
	    
	    btn6.setBackground(btn_color);
	    btn6.setForeground(Color.white);
	    btn6.setFont(font);

	    // ��ư ��ġ�� ũ�� ����
	    btn1.setBounds(180,129,300,100);
	    btn2.setBounds(520,129,300,100);
 	    btn3.setBounds(180,272,300,100);
    	btn4.setBounds(520,272,300,100);
		btn5.setBounds(180,415,300,100);
		btn6.setBounds(520,415,300,100);
	
		// �����ӿ��ٰ� ��ư �߰�
		contentPane.add(userName);
		contentPane.add(btn1);
		contentPane.add(btn2);
		contentPane.add(btn3);
		contentPane.add(btn4);
		contentPane.add(btn5);
		contentPane.add(btn6);
		
		//��ư Ŭ���� �̵� - ����
		//��ǰ ��� ��ư
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_ProductRegister();
			}
		});
		//��ǰ �� ��ư
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_ProductDetail();
			}
		});
		//�˻� ��ư
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_Search();
			}
		});
		//����������-���ø���Ʈ ��ư
		btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_WishList();
			}
		});
		
		//�ŷ����� ��ư
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DB2022TEAM01_TradeList();
			}
		});
		//���̵� ����Ʈ ��ư
		btn6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_IdolList();
			}
		});
		
		setSize(1000, 700);
		setResizable(false);
		setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		setVisible(true);
	}
	//Ȩ ��ư ����� �Լ�
	public static JButton make_home() {
		ImageIcon icon = new ImageIcon("home.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon home = new ImageIcon(changeImg);
		JButton home_btn = new JButton(home);
		home_btn.setBackground(Color.white);
		home_btn.setFocusPainted(false);
		home_btn.setToolTipText("Ȩ");
		return home_btn;
	}
}
