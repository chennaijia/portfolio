import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Book {
	String server = "jdbc:mysql://140.119.19.73:3315/";
    String database = "112306070";
    String url = server + database + "?useSSL=false";
    String username = "112306070";
    String password = "dqocn";
	
	private int id;
	private String lesson;
	private String title;
	private String author;
	private String depreciation;
	private String note;
	private String condition;
	private String price;
	private String imagePath;
	private String college;
	private String department;
	private String grade;
	private String lessonType;
	private String bookType;
	private String type;
	private String wtcPlatform, wtcName;

	public Book(int id, String lesson, String title, String author, String depreciation, String note, String condition,
			String price, String imagePath, String college, String department, String grade, String lessonType, String bookType, String type) {
		this.id = id;
		this.lesson = lesson;
		this.title = title;
		this.author = author;
		this.depreciation = depreciation;
		this.note = note;
		this.condition = condition;
		this.price = price;
		this.imagePath = imagePath;
		this.college = college;
		this.department = department;
		this.grade = grade;
		this.lessonType = lessonType;
		this.bookType = bookType;
		this.type = type;
		
		String query = "SELECT * FROM `FinalProject` WHERE ID = ?";
		
		try (Connection conn = DriverManager.getConnection(url, username, password);
	            PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, id);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	            	this.wtcName = rs.getString("wayToConnect");
	            	 this.wtcPlatform = rs.getString("platform");
	                }
	        } catch (SQLException e) {
	            System.err.println("資料庫連線或查詢失敗：" + e.getMessage());
	        }
	}

	public int getID() {
		return id;
	}

	public String getLesson() {
		return lesson;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDepreciation() {
		return depreciation;
	}

	public String getNote() {
		return note;
	}

	public String getCondition() {
		return condition;
	}

	public String getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}
	
	public String getCollege() {
		return college;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public String getLessonType() {
		return lessonType;
	}
	
	public String getBookType() {
		return bookType;
	}
	
	public String getType() {
		return type;
	}

	public String getwtc() {
		return wtcPlatform + " " + wtcName;
	}

}