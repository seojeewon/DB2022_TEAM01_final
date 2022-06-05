import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class DB2022TEAM01_Search extends JFrame{
	private String [] categories = {"����ī��", "�ٹ�", "����", "����׸���", "����ŰƮ", "������̵�", "������", "����", "��Ÿ"};
	private String group_info = "*���̵� �׷�";
	private String mem_info = "*�����";
	private String key_info = "�˻� Ű����";
		
	public DB2022TEAM01_Search() {
		setTitle("�˻�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
		//������ Ÿ��Ʋ: <�˻�>
		JLabel title = new JLabel("�˻�", SwingConstants.CENTER);	
		title.setBounds(463, 120, 75, 49);
		Font font1 = new Font("���� ���", Font.BOLD, 35);
		title.setFont(font1);		
		contentPane.add(title);
		
		//�ȳ� ����
		JLabel alert = new JLabel("*�� �ݵ�� �Է��ϼ���.");
		Font font1_1 = new Font("���� ���", Font.PLAIN, 15);
		alert.setBounds(145,194,160,18);
		alert.setFont(font1_1);
		contentPane.add(alert);
		
		//�׷��, �����, Ű���� �Է�â
		JTextField group = new JTextField(group_info, 40);
		JTextField member = new JTextField(mem_info, 40);
		JTextField keyword = new JTextField(key_info, 80);
		//ī�װ� ���� �޺��ڽ�
		JComboBox<String> category = new JComboBox<>(categories);
		
		
		//�Է�â�� ��ġ ����
		group.setBounds(145, 226, 243, 63);
		member.setBounds(420, 226, 209, 63);
		keyword.setBounds(145, 319, 484, 63);
		category.setBounds(661, 226, 168, 63);			
		
		//��Ʈ ����
		Font font2 = new Font("���� ���", Font.PLAIN, 23);
		group.setFont(font2);
		member.setFont(font2);
		keyword.setFont(font2);
		category.setFont(font2);
		
		//��Ŀ���Ǹ� ��ü����->�ٷ� �Է� ����
		MyFocusListener listener = new MyFocusListener();
		group.addFocusListener(listener);
		member.addFocusListener(listener);
		keyword.addFocusListener(listener);
		
		//�� â�� ���� ����
		group.setToolTipText("���̵� �׷�");
		member.setToolTipText("�����");
		keyword.setToolTipText("�˻� Ű����");
		
		//�˻� ��ư		
		JButton search_btn = new JButton("Search");
		Color btn_color=new Color(0xFF6472);
		search_btn.setBackground(btn_color);
		search_btn.setForeground(Color.white);
		search_btn.setFont(font2);
		search_btn.setBounds(438, 412, 125, 63);	
		
		JButton home = DB2022TEAM01_MyPage.make_home();
        home.setBounds(950, 5, 30, 30);
				
		contentPane.add(member);
		contentPane.add(group);
		contentPane.add(keyword);
		contentPane.add(category);
		contentPane.add(search_btn);
		contentPane.add(home);
		
		search_btn.addActionListener(new ActionListener() {	//�˻� ��ư Ŭ��
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String idol_group = group.getText();
				String idol_mem = member.getText();
				String keyword_str = keyword.getText();
				String category_str = category.getSelectedItem().toString();
				if(keyword_str.equals(key_info)){
					keyword_str = "";
				}
				if(idol_group.isBlank() || idol_group.equals(group_info) || idol_mem.isBlank() || idol_mem.equals(mem_info)) {	//�ʼ� �Է� ���� �Է� ������ ��
					JOptionPane.showMessageDialog(DB2022TEAM01_Search.this, "���̵� �׷�� ������� �Է��ؾ� �մϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//��ǰ�������� ������.
					DB2022TEAM01_SearchDAO dao = new DB2022TEAM01_SearchDAO();

					dispose();
					new DB2022TEAM01_SearchView(idol_group, idol_mem, keyword_str, category_str);
				}
					
				
			}
		});
		
		//Ȩ��ư
        home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_MyPage();
			}
		});
		
		setSize(1000, 700);
		setResizable(false);
		setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		setVisible(true);
	}
	
	//focuslistener
	class MyFocusListener implements FocusListener{	//�Է¶� Ŭ���� ��ü ���� -> Ŭ���ϸ� ������ �����ϴ� ���ڰ� ��� ���õǾ� �ٷ� Ű���� �Է� ���� �� �ְ� ��.
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			JTextField f = (JTextField)e.getSource();
			f.selectAll();
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}	

		
	}
}
