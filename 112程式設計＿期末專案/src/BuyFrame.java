import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class BuyFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private int id;
    private User user;
    private String title, price, imagePath;
    private HomeFrame homeFrame;
    private String platform, wtc, sellerContact;

    public BuyFrame(int sellerID, User user, String title, String price, String imagePath, HomeFrame homeFrame) {
        id = sellerID;
        this.user = user;
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.homeFrame = homeFrame;

        String server = "jdbc:mysql://140.119.19.73:3315/";
        String database = "112306070";
        String url = server + database + "?useSSL=false";
        String username = "112306070";
        String password = "dqocn";

        String query = "SELECT * FROM `FinalProject` WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                platform = rs.getString("platform");
                wtc = rs.getString("wayToConnect");
                sellerContact = platform + " " + wtc;
            } else {
                JOptionPane.showMessageDialog(null, "No seller found with ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
                setVisible(false);
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
            return;
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("賣家資訊");
        lblNewLabel.setBounds(48, 51, 61, 16);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("學號");
        lblNewLabel_1.setBounds(121, 51, 61, 16);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("聯絡方式");
        lblNewLabel_2.setBounds(121, 86, 61, 16);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel(Integer.toString(id));
        lblNewLabel_3.setBounds(215, 51, 143, 16);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel(sellerContact);
        lblNewLabel_4.setBounds(215, 86, 189, 16);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("面交時間、地點");
        lblNewLabel_5.setBounds(48, 130, 134, 16);
        contentPane.add(lblNewLabel_5);

        textField = new JTextField();
        textField.setBounds(215, 125, 197, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("確認");
        btnNewButton.setBounds(84, 204, 117, 29);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String server = "jdbc:mysql://140.119.19.73:3315/";
                String database = "112306070";
                String url = server + database + "?useSSL=false";
                String username = "112306070";
                String password = "dqocn";

                String query = "INSERT INTO `FinalProject_Buy` (`BuyerID`, `SellerID`, `BookTitle`, `price`, `Image`, `TimeAndLocation`) VALUES (?, ?, ?, ?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(url, username, password);
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setInt(1, user.getID());
                    pstmt.setInt(2, id);
                    pstmt.setString(3, title);
                    pstmt.setString(4, price);
                    pstmt.setString(5, imagePath);
                    pstmt.setString(6, textField.getText());
                    pstmt.executeUpdate();
                    homeFrame.removeBookFromDatabase(id, title);
                    JOptionPane.showMessageDialog(null, "已購買", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "加入購買失敗: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                setVisible(false);
            }
        });

        JButton btnNewButton_1 = new JButton("取消");
        btnNewButton_1.setBounds(233, 204, 117, 29);
        contentPane.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
