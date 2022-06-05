import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class DB2022TEAM01_Search extends JFrame{
	private String [] categories = {"포토카드", "앨범", "인형", "시즌그리팅", "공식키트", "폴라로이드", "포스터", "잡지", "기타"};
	private String group_info = "*아이돌 그룹";
	private String mem_info = "*멤버명";
	private String key_info = "검색 키워드";
		
	public DB2022TEAM01_Search() {
		setTitle("검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
		//페이지 타이틀: <검색>
		JLabel title = new JLabel("검색", SwingConstants.CENTER);	
		title.setBounds(463, 120, 75, 49);
		Font font1 = new Font("맑은 고딕", Font.BOLD, 35);
		title.setFont(font1);		
		contentPane.add(title);
		
		//안내 문구
		JLabel alert = new JLabel("*은 반드시 입력하세요.");
		Font font1_1 = new Font("맑은 고딕", Font.PLAIN, 15);
		alert.setBounds(145,194,160,18);
		alert.setFont(font1_1);
		contentPane.add(alert);
		
		//그룹명, 멤버명, 키워드 입력창
		JTextField group = new JTextField(group_info, 40);
		JTextField member = new JTextField(mem_info, 40);
		JTextField keyword = new JTextField(key_info, 80);
		//카테고리 선택 콤보박스
		JComboBox<String> category = new JComboBox<>(categories);
		
		
		//입력창의 위치 설정
		group.setBounds(145, 226, 243, 63);
		member.setBounds(420, 226, 209, 63);
		keyword.setBounds(145, 319, 484, 63);
		category.setBounds(661, 226, 168, 63);			
		
		//폰트 설정
		Font font2 = new Font("맑은 고딕", Font.PLAIN, 23);
		group.setFont(font2);
		member.setFont(font2);
		keyword.setFont(font2);
		category.setFont(font2);
		
		//포커스되면 전체선택->바로 입력 가능
		MyFocusListener listener = new MyFocusListener();
		group.addFocusListener(listener);
		member.addFocusListener(listener);
		keyword.addFocusListener(listener);
		
		//각 창에 대한 도움말
		group.setToolTipText("아이돌 그룹");
		member.setToolTipText("멤버명");
		keyword.setToolTipText("검색 키워드");
		
		//검색 버튼		
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
		
		search_btn.addActionListener(new ActionListener() {	//검색 버튼 클릭
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String idol_group = group.getText();
				String idol_mem = member.getText();
				String keyword_str = keyword.getText();
				String category_str = category.getSelectedItem().toString();
				if(keyword_str.equals(key_info)){
					keyword_str = "";
				}
				if(idol_group.isBlank() || idol_group.equals(group_info) || idol_mem.isBlank() || idol_mem.equals(mem_info)) {	//필수 입력 사항 입력 안했을 때
					JOptionPane.showMessageDialog(DB2022TEAM01_Search.this, "아이돌 그룹과 멤버명을 입력해야 합니다.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//상품상세페이지 보여줌.
					DB2022TEAM01_SearchDAO dao = new DB2022TEAM01_SearchDAO();

					dispose();
					new DB2022TEAM01_SearchView(idol_group, idol_mem, keyword_str, category_str);
				}
					
				
			}
		});
		
		//홈버튼
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
		setLocationRelativeTo(null);	//화면 중앙에 뜸
		setVisible(true);
	}
	
	//focuslistener
	class MyFocusListener implements FocusListener{	//입력란 클릭시 전체 선택 -> 클릭하면 기존에 존재하던 글자가 모두 선택되어 바로 키보드 입력 받을 수 있게 함.
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
