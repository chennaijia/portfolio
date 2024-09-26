import java.sql.*;
import javax.swing.JOptionPane;


public class User {
	String server = "jdbc:mysql://140.119.19.73:3315/";
    String database = "112306070";
    String url = server + database + "?useSSL=false";
    String username = "112306070";
    String password = "dqocn";
    int id;
    String password1;
    String wtc;
    String platform;

	public boolean add(String name, String pw, String pwc, String selectedPlatform, String wayToConnect) throws PasswordError, UserError, WTCError {
		//pw = password
		//pwc = password check
		
		boolean done = false;
		
		if (name.length() == 0) 
			throw new UserError("未輸入學號");
		else if (name.length() != 9) 
			throw new UserError("學號輸入格式錯誤");
		else if (pw.length() != 8)
			throw new PasswordError("密碼應為八位數字/字母");
		else if (!pw.equals(pwc))
			throw new PasswordError("再次輸入密碼錯誤");
		else if (wayToConnect.length() == 0)
			throw new Error("未填寫聯絡方式");
		else if (selectedPlatform.equals("平台"))
			throw new Error("未選擇聯絡方式平台");
		else
			done = true;
		
		int id = Integer.parseInt(name);
		
		String query = "SELECT * FROM `FinalProject` WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	done = false;
                throw new UserError("此帳戶已存在");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("資料庫連線或查詢失敗：" + e.getMessage());
        }
		
		if(done) {
			try (Connection conn = DriverManager.getConnection(url, username, password)) {
				String insertQuery = "INSERT INTO `FinalProject` (ID,password,platform,wayToConnect) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                pstmt.setInt(1, id);
                pstmt.setString(2, pw);
                pstmt.setString(3, selectedPlatform);
                pstmt.setString(4, wayToConnect);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
			 } catch (SQLException e) {
		            e.printStackTrace();
		     }
		}
		return done;
	}

	public boolean checkUserExist(int id, String pw) throws UserError, PasswordError {
		boolean done = false;
		
		String query = "SELECT * FROM `FinalProject` WHERE ID = ?";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	 String password = rs.getString("password");
            	 String wtc = rs.getString("wayToConnect");
            	 String platform = rs.getString("platform");
                if(pw.equals(password)) {
                	this.id = id;
                    password1 = password;
                    this.wtc = wtc;
                    this.platform = platform;
                	done = true;
                	return done;
                }
                throw new PasswordError("密碼錯誤");
            }else 
        		throw new UserError("找不到此帳戶");
        } catch (SQLException e) {
            System.err.println("資料庫連線或查詢失敗：" + e.getMessage());
        }
		return done;
	}
	
	public int getID() {
		return id;
	}
	
	public String getWtcPlatform() {
		return platform;
	}
	
	public String getWtcName() {
		return wtc;
	}
	
}

class UserError extends Exception {
	
	public UserError(String Error){
		super(Error);
	}
}

class PasswordError extends Exception {
	
	public PasswordError(String Error){ 
		super(Error);
	}
}

class WTCError extends Exception {
	
	public WTCError(String Error){
		super(Error);
	}
}