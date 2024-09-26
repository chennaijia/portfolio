import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private User user;
    private HomeFrame frame;
    private JTextField tfUserName;
    private JPasswordField tfPassword;
    private JButton btnLogin, btnBack;

    public LoginFrame(User user) {
        // Login
        this.user = user;
        this.setTitle("登入");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);

        final int FIELD_WIDTH = 20;
        tfUserName = new JTextField(FIELD_WIDTH);
        tfPassword = new JPasswordField(FIELD_WIDTH);

        tfUserName.setText("");
        tfPassword.setText("");

        btnLogin = new JButton("登入");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        tfUserName.addKeyListener(new EnterKeyListener());
        tfPassword.addKeyListener(new EnterKeyListener());

        btnBack = new JButton("返回");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
                setVisible(false);
            }
        });

        createLayout();
    }

    private void login() {
        String username = tfUserName.getText();
        int id = Integer.parseInt(username);
        char[] passwordChar = tfPassword.getPassword();
        String password = new String(passwordChar);

        try {
            if (user.checkUserExist(id, password)) {
                String info = String.format("登入成功！");
                JOptionPane.showMessageDialog(null, info, "Success", JOptionPane.INFORMATION_MESSAGE);
                frame = new HomeFrame(user);
                frame.setVisible(true);
                setVisible(false);
            }
        } catch (UserError e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            e1.printStackTrace();
        } catch (PasswordError e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            e1.printStackTrace();
        }
    }

    private class EnterKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private void createLayout() {
        JPanel uPanel = new JPanel();
        uPanel.add(new JLabel("學號："));
        uPanel.add(tfUserName);
        uPanel.setPreferredSize(new Dimension(500, 40));

        JPanel pPanel = new JPanel();
        pPanel.add(new JLabel("密碼："));
        pPanel.add(tfPassword);
        pPanel.setPreferredSize(new Dimension(500, 40));

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnBack);
        btnPanel.add(btnLogin);

        JPanel allPannel = new JPanel();
        allPannel.add(uPanel);
        allPannel.add(pPanel);
        allPannel.add(btnPanel);

        setLayout(new BorderLayout(20, 40));
        getContentPane().add(new JPanel(), BorderLayout.NORTH);
        getContentPane().add(allPannel, BorderLayout.CENTER);
        getContentPane().add(new JPanel(), BorderLayout.SOUTH);
    }
}
