import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartFrame extends JFrame {
	private LoginFrame loginFrame;
	private SignUpFrame signUpFrame;
	private JButton btnEnroll, btnLogin;
	private User user;

	public StartFrame() {
		
		user = new User();
		
        this.setTitle("Start");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 200);
	
		btnLogin = new JButton("登入");
        btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame = new LoginFrame(user);
				loginFrame.setVisible(true);
				setVisible(false);
			}
		});
        
        btnEnroll = new JButton("註冊");
        btnEnroll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        SignUpFrame signUpFrame = new SignUpFrame(user);
                        signUpFrame.setVisible(true);
                        setVisible(false);
                        HomeFrame frame = new HomeFrame(user);
                      
                    }
                });
            }
        });

		createLayout();
	}

	private void createLayout() {

		JPanel bPanel = new JPanel();
		bPanel.add(btnLogin);
		bPanel.add(btnEnroll);
		bPanel.setPreferredSize(new Dimension(500, 40));

		JPanel panel = new JPanel();
		panel.add(new JLabel("歡迎使用二手書交易平台！"));
		panel.add(bPanel);
		
		
		setLayout(new BorderLayout(20, 40));
		getContentPane().add(new JPanel(), BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(new JPanel(), BorderLayout.SOUTH);
	}
		
		public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    StartFrame frame = new StartFrame();
	                    frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
}