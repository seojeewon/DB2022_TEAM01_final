import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DB2022TEAM01_LogIn extends JFrame{
	public DB2022TEAM01_LogIn() {
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
		//������ Ÿ��Ʋ <���̵� �������>
		JLabel title = new JLabel("���̵� �������", SwingConstants.CENTER);	
		title.setBounds(320, 110, 360, 50);
		Font font1 = new Font("���� ���", Font.BOLD, 35);
		title.setFont(font1);		
		contentPane.add(title);
		
		//id, pw �Է�â
		JLabel id = new JLabel("ID");
		JLabel pw = new JLabel("PW");
		JTextField id_field = new JTextField(20);
		JPasswordField pw_field = new JPasswordField(20);
		id.setBounds(320, 200, 40, 40);
		pw.setBounds(320, 260, 40, 40);
		id_field.setBounds(380, 200, 300, 40);
		pw_field.setBounds(380, 260, 300, 40);
		
		
		Font font2 = new Font("Arial", Font.BOLD, 23);
		id.setFont(font2);
		pw.setFont(font2);
		
		contentPane.add(id);		
		contentPane.add(id_field);
		contentPane.add(pw);
		contentPane.add(pw_field);
		
		
		//�α���, ȸ������ ��ư
		JButton login = new JButton("LogIn");				
		JButton signup = new JButton("SignUp");		
		
		//��ư ����, ��Ʈ
		Color btn_color=new Color(0xFF6472);
		login.setBackground(btn_color);
		login.setForeground(Color.white);
		login.setFont(font2);
		signup.setBackground(btn_color);		
		signup.setForeground(Color.white);
		signup.setFont(font2);
		
		login.setBounds(320, 340, 170, 50);
		signup.setBounds(510, 340, 170, 50);
		
		contentPane.add(login);
		contentPane.add(signup);

		
		//login ��ư Ŭ��
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id_value = id_field.getText();
				String pw_value = new String(pw_field.getPassword());
				DB2022TEAM01_UserDAO user_login = new DB2022TEAM01_UserDAO();

				if(user_login.login(id_value, pw_value)==1) {	//�α��� ����
					dispose();
					new DB2022TEAM01_MyPage();
				}
				else if(user_login.login(id_value, pw_value)==0) {	//��й�ȣ Ʋ��
					JOptionPane.showMessageDialog(DB2022TEAM01_LogIn.this, "��й�ȣ�� Ʋ�Ƚ��ϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {	//���̵� ����
					JOptionPane.showMessageDialog(DB2022TEAM01_LogIn.this, "��ϵ��� ���� ���̵��Դϴ�.", "Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//SignUp ��ư Ŭ���� ȸ������ �������� �̵�
		signup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DB2022TEAM01_SignUp();
			}
		});
		
		setSize(1000, 700);
		setResizable(false);
		setLocationRelativeTo(null);	//ȭ�� �߾ӿ� ��
		setVisible(true);





	}
}
