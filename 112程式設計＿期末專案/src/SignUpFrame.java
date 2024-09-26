import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;

public class SignUpFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField id, tfwtc;
	private JButton btnSignUp, btnBack;
	private JLabel idLabel, passwordLabel, passwordCheckLabel, wtcLabel;
	private JComboBox<String> wtcCombo;
	private JPasswordField tfpassword, tfpasswordCheck;
	private LoginFrame frame;
	private User user;

	public SignUpFrame(User user) {
		
		this.user = user;
		
		setTitle("註冊");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		createLayout();
	}
	
	private void createLayout() {
		idLabel = new JLabel("學號");
		idLabel.setBounds(56, 38, 61, 16);
		contentPane.add(idLabel);
		
		id = new JTextField();
		id.setBounds(170, 33, 227, 26);
		contentPane.add(id);
		id.setColumns(10);
		
		passwordLabel = new JLabel("密碼");
		passwordLabel.setBounds(56, 66, 61, 16);
		contentPane.add(passwordLabel);
		
		tfpassword = new JPasswordField();
		tfpassword.setBounds(170, 61, 227, 26);
		contentPane.add(tfpassword);
		tfpassword.setColumns(10);
		
		passwordCheckLabel = new JLabel("再次確認密碼");
		passwordCheckLabel.setBounds(56, 94, 90, 16);
		contentPane.add(passwordCheckLabel);
		
		tfpasswordCheck = new JPasswordField();
		tfpasswordCheck.setBounds(170, 89, 227, 26);
		contentPane.add(tfpasswordCheck);
		tfpasswordCheck.setColumns(10);
		
		wtcLabel = new JLabel("聯絡方式");
		wtcLabel.setBounds(56, 122, 61, 16);
		contentPane.add(wtcLabel);
		
		wtcCombo = new JComboBox<>(new String[]{"平台", "Instagram", "Facebook"});
		wtcCombo.setBounds(170, 118, 145, 27);
		contentPane.add(wtcCombo);
		
		tfwtc = new JTextField();
		tfwtc.setBounds(170, 146, 227, 26);
		contentPane.add(tfwtc);
		tfwtc.setColumns(10);
		
		btnSignUp = new JButton("確認註冊");
		btnSignUp.setBounds(236, 195, 117, 29);
		contentPane.add(btnSignUp);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String userName = id.getText();
				String password = new String(tfpassword.getPassword());
				String checkPassword = new String(tfpasswordCheck.getPassword());
				String wayToConnect = tfwtc.getText();
				String selectedPlatform = (String) wtcCombo.getSelectedItem();
				try {
					if(user.add(userName, password, checkPassword, selectedPlatform, wayToConnect)) {
						String info = String.format("註冊成功！\n學號：%s\n密碼：%s\n聯絡方式：%s-%s", userName, password, selectedPlatform, wayToConnect);
						JOptionPane.showMessageDialog(null, info, "Success", JOptionPane.INFORMATION_MESSAGE);
						frame = new LoginFrame(user);
						frame.setVisible(true);
						setVisible(false);
					}
				} catch (PasswordError e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1,"Error", JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}catch (UserError e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1,"Error", JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}catch (WTCError e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1,"Error", JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		btnBack = new JButton("返回");
		btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
                setVisible(false);
            }
        });
		btnBack.setBounds(89, 195, 117, 29);
		contentPane.add(btnBack);
	}
}