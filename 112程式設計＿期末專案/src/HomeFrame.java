import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeFrame extends JFrame {
	private User user;
	private List<Book> bookList = new ArrayList<>();
	private List<Book> cartList = new ArrayList<>();
	private JPanel formPanel, shopPanel, cartPanel, soldPanel, soldBooksPanel, dealPanel, infoPanel;
	private JTabbedPane tab;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel idLabel;
	private JLabel wtcLabel;
	private JLabel soldAmountLabel;
	private JLabel dealAmountLabel;
	private JButton logOutButton;
	private JButton editWtcButton;
	private BuyFrame buyFrame;
	private ArrayList<Book> bookUserSold = new ArrayList<>();
	private JComboBox<String> collegeComboBox, yearComboBox, courseComboBox, noteComboBox, departmentComboBox,
			typeComboBox;

	// 加入賣場
	private static final long serialVersionUID = 1L;
	private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5;
	private String select1, select2, select3, select4, select5, select6;
	private String selectedImagePath;

	public HomeFrame(User user) {
		this.user = user;
		this.setTitle("主頁");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		initializePanels();
		initializeTabs();
		showSoldPanel();
		updateShopPanel(select1);
		updateCartPanel();
		loadBooksFromDatabase();
	}
	
	private void initializePanels() {
		shopPanel = new JPanel();
		soldPanel = new JPanel();
		soldBooksPanel = new JPanel();
		cartPanel = new JPanel();
		dealPanel = new JPanel();
		infoPanel = new JPanel();
		infoPanel.setLayout(null);

		shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
		// soldPanel.setLayout(new BoxLayout(soldPanel, BoxLayout.Y_AXIS));
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
		dealPanel.setLayout(new BoxLayout(dealPanel, BoxLayout.Y_AXIS));

	}

	private void initializeTabs() {
		tab = new JTabbedPane();
		tab.addTab("逛一逛", new JScrollPane(shopPanel));
		tab.addTab("我的賣場", new JScrollPane(soldPanel));
		tab.addTab("購物車", new JScrollPane(cartPanel));
		tab.addTab("成交歷史紀錄", new JScrollPane(dealPanel));
		tab.addTab("我的資料", infoPanel);
		getContentPane().add(tab);

		tab.addChangeListener(e -> {
			int selectedIndex = tab.getSelectedIndex();
			if (tab.getTitleAt(selectedIndex).equals("我的賣場")) {
				updateSoldPanel();
			} else if (tab.getTitleAt(selectedIndex).equals("逛一逛")) {
				updateShopPanel(select1);
			} else if (tab.getTitleAt(selectedIndex).equals("購物車")) {
				updateCartPanel();
			} else if (tab.getTitleAt(selectedIndex).equals("我的資料")) {
				setMyInfoPage();
			} else if (tab.getTitleAt(selectedIndex).equals("成交歷史紀錄")) {
				BuyPage();
			}
		});
	}

	private void showTextBookPanel() {
		formPanel.removeAll();
		select6 = "教科書";
		formPanel.setLayout(new GridLayout(0, 2));

		typeComboBox = new JComboBox<>();
		JLabel collegeLabel = new JLabel("學院:");
		collegeComboBox = new JComboBox<>();
		collegeComboBox.addItem("請選擇");
		collegeComboBox.addItem("文學院");
		collegeComboBox.addItem("社會科學學院");
		collegeComboBox.addItem("商學院");
		collegeComboBox.addItem("傳播學院");
		collegeComboBox.addItem("外國語文學院");
		collegeComboBox.addItem("法學院");
		collegeComboBox.addItem("理學院");
		collegeComboBox.addItem("國際事務學院");
		collegeComboBox.addItem("教育學院");
		collegeComboBox.addItem("創新國際學院");
		collegeComboBox.addItem("資訊學院");
		collegeComboBox.addItem("通識課程");
		collegeComboBox.addItem("其他課程");

		formPanel.add(collegeLabel);
		formPanel.add(collegeComboBox);

		JLabel departmentLabel = new JLabel("系所:");
		departmentComboBox = new JComboBox<>();
		departmentComboBox.setEnabled(false);

		collegeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedCollege = (String) collegeComboBox.getSelectedItem();
				departmentComboBox.removeAllItems();
				if (selectedCollege != null && !selectedCollege.equals("請選擇")) {
					departmentComboBox.setEnabled(true);
					switch (selectedCollege) {
					case "文學院":
						departmentComboBox.addItem("中國文學系");
						departmentComboBox.addItem("歷史學系");
						departmentComboBox.addItem("哲學系");
						break;
					case "社會科學學院":
						departmentComboBox.addItem("政治學系");
						departmentComboBox.addItem("社會學系");
						departmentComboBox.addItem("財政學系");
						departmentComboBox.addItem("公共行政學系");
						departmentComboBox.addItem("地政學系");
						departmentComboBox.addItem("經濟學系");
						departmentComboBox.addItem("民族學系");
						break;
					case "商學院":
						departmentComboBox.addItem("國際經營與貿易學系");
						departmentComboBox.addItem("金融學系");
						departmentComboBox.addItem("會計學系");
						departmentComboBox.addItem("統計學系");
						departmentComboBox.addItem("企業管理學系");
						departmentComboBox.addItem("資訊管理學系");
						departmentComboBox.addItem("財務管理學系");
						departmentComboBox.addItem("風險管理與保險學系");
						select2 = (String) departmentComboBox.getSelectedItem();
						break;
					case "傳播學院":
						departmentComboBox.addItem("傳播學院大一大二不分系");
						departmentComboBox.addItem("新聞學系");
						departmentComboBox.addItem("廣告學系");
						departmentComboBox.addItem("廣播電視學系");
						break;
					case "外國語文學院":
						departmentComboBox.addItem("英國語文學系");
						departmentComboBox.addItem("阿拉伯語文學系");
						departmentComboBox.addItem("斯拉夫語文學系");
						departmentComboBox.addItem("日本語文學系");
						departmentComboBox.addItem("韓國語文學系");
						departmentComboBox.addItem("土耳其語文學系");
						departmentComboBox.addItem("歐洲語文學系");
						departmentComboBox.addItem("東南亞語言與文化學士學位學程");
						break;
					case "法學院":
						departmentComboBox.addItem("法律學系");
						break;
					case "理學院":
						departmentComboBox.addItem("應用數學系");
						departmentComboBox.addItem("心理學系");
						departmentComboBox.addItem("電子物理學士學位學程");
						break;
					case "國際事務學院":
						departmentComboBox.addItem("外交學系");
						break;
					case "教育學院":
						departmentComboBox.addItem("教育學系");
						break;
					case "創新國際學院":
						departmentComboBox.addItem("創新國際學院學士班");
						break;
					case "資訊學院":
						departmentComboBox.addItem("資訊科學系");
						departmentComboBox.addItem("數位內容與科技學士學位學程");
						departmentComboBox.addItem("人工智慧運用學士學位學程");
						break;
					default:
						departmentComboBox.setEnabled(false);
						break;
					}
				} else {
					departmentComboBox.setEnabled(false);
				}

			}
		});
		formPanel.add(departmentLabel);
		formPanel.add(departmentComboBox);

		JLabel yearLabel = new JLabel("年級:");
		yearComboBox = new JComboBox<>();
		yearComboBox.addItem("請選擇");
		yearComboBox.addItem("一年級");
		yearComboBox.addItem("二年級");
		yearComboBox.addItem("三年級");
		yearComboBox.addItem("四年級");

		formPanel.add(yearLabel);
		formPanel.add(yearComboBox);

		JLabel courseLabel = new JLabel("必修/選修/通識:");
		courseComboBox = new JComboBox<>();
		courseComboBox.setEnabled(false);
		collegeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedCollege = (String) collegeComboBox.getSelectedItem();
				courseComboBox.removeAllItems();
				if (selectedCollege != null && !selectedCollege.equals("請選擇")) {
					courseComboBox.setEnabled(true);
					courseComboBox.addItem("必修");
					courseComboBox.addItem("選修");
					courseComboBox.addItem("通識-中文通識");
					courseComboBox.addItem("通識-外文通識");
					courseComboBox.addItem("通識-人文通識");
					courseComboBox.addItem("通識-社會通識");
					courseComboBox.addItem("通識-自然通識");
					courseComboBox.addItem("通識-跨域通識");
					courseComboBox.addItem("通識-資訊通識");
					courseComboBox.addItem("通識-書院通識");
				} else {
					courseComboBox.setEnabled(false);
				}
			}
		});
		formPanel.add(courseLabel);
		formPanel.add(courseComboBox);

		// Other fields
		JLabel titleLabel = new JLabel("課程名稱");
		textField = new JTextField();
		formPanel.add(titleLabel);
		formPanel.add(textField);

		JLabel bookTitleLabel = new JLabel("書名");
		textField_1 = new JTextField();
		formPanel.add(bookTitleLabel);
		formPanel.add(textField_1);

		JLabel authorLabel = new JLabel("作者");
		textField_2 = new JTextField();
		formPanel.add(authorLabel);
		formPanel.add(textField_2);

		JLabel newOldLabel = new JLabel("書本新舊（幾成新）");
		textField_3 = new JTextField();
		formPanel.add(newOldLabel);
		formPanel.add(textField_3);

		JLabel noteLabel = new JLabel("筆記有無");
		noteComboBox = new JComboBox<>();
		noteComboBox.addItem("有");
		noteComboBox.addItem("無");
		formPanel.add(noteLabel);
		formPanel.add(noteComboBox);

		JLabel bookConditionLabel = new JLabel("書況概述:");
		textField_4 = new JTextField();
		formPanel.add(bookConditionLabel);
		formPanel.add(textField_4);

		JLabel priceLabel = new JLabel("價格");
		textField_5 = new JTextField();
		formPanel.add(priceLabel);
		formPanel.add(textField_5);

		JButton submitButton = new JButton("加入賣場");
		submitButton.addActionListener(e -> {
			try {
				submitBook();
				JOptionPane.showMessageDialog(soldPanel, "書籍已成功加入賣場", "成功", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(soldPanel, "價格必須為數字", "錯誤", JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton chooseImageButton = new JButton("選擇圖片");
		JLabel imageLabel = new JLabel();

		chooseImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					selectedImagePath = selectedFile.getPath();
					ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(100,
							150, Image.SCALE_DEFAULT));
					imageLabel.setIcon(icon);
				}
			}
		});

		JPanel btnCombinePanel = new JPanel();
		btnCombinePanel.add(chooseImageButton);
		btnCombinePanel.add(submitButton);

		formPanel.add(btnCombinePanel, BorderLayout.NORTH);
		formPanel.revalidate();
		formPanel.repaint();
	}

	private void showNoTextBookPanel() {
		formPanel.removeAll();
		select6 = "非教科書";
		formPanel.setLayout(new GridLayout(0, 2));
		courseComboBox = new JComboBox<>();
		yearComboBox = new JComboBox<>();
		collegeComboBox = new JComboBox<>();

		JLabel typeLabel = new JLabel("類別:");
		typeComboBox = new JComboBox<>();
		typeComboBox.addItem("請選擇");
		typeComboBox.addItem("小說");
		typeComboBox.addItem("英檢用書");
		typeComboBox.addItem("其他");

		formPanel.add(typeLabel);
		formPanel.add(typeComboBox);

		JLabel bookTitleLabel = new JLabel("書名");
		textField_1 = new JTextField();
		formPanel.add(bookTitleLabel);
		formPanel.add(textField_1);

		JLabel authorLabel = new JLabel("作者");
		textField_2 = new JTextField();
		formPanel.add(authorLabel);
		formPanel.add(textField_2);

		JLabel newOldLabel = new JLabel("書本新舊（幾成新）");
		textField_3 = new JTextField();
		formPanel.add(newOldLabel);
		formPanel.add(textField_3);

		JLabel noteLabel = new JLabel("筆記有無");
		noteComboBox = new JComboBox<>();
		noteComboBox.addItem("有");
		noteComboBox.addItem("無");
		formPanel.add(noteLabel);
		formPanel.add(noteComboBox);

		JLabel bookConditionLabel = new JLabel("書況概述:");
		textField_4 = new JTextField();
		formPanel.add(bookConditionLabel);
		formPanel.add(textField_4);

		JLabel priceLabel = new JLabel("價格");
		textField_5 = new JTextField();
		formPanel.add(priceLabel);
		formPanel.add(textField_5);

		JButton submitButton = new JButton("加入賣場");
		submitButton.addActionListener(e -> {
			try {
				submitBook();
				JOptionPane.showMessageDialog(soldPanel, "書籍已成功加入賣場", "成功", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(soldPanel, "價格必須為數字", "錯誤", JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton chooseImageButton = new JButton("選擇圖片");
		JLabel imageLabel = new JLabel();

		chooseImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					selectedImagePath = selectedFile.getPath();
					ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(100,
							150, Image.SCALE_DEFAULT));
					imageLabel.setIcon(icon);
				}
			}
		});

		JPanel btnCombinePanel = new JPanel();
		btnCombinePanel.add(chooseImageButton);
		btnCombinePanel.add(submitButton);

		formPanel.add(btnCombinePanel, BorderLayout.NORTH);

		formPanel.revalidate();
		formPanel.repaint();
	}

	// 加入賣場
	// 加入賣場介面
	private void showSoldPanel() {
		soldPanel.removeAll();

		soldPanel.setLayout(new BorderLayout());
		formPanel = new JPanel();

		JButton textBookButton = new JButton("教科書");
		JButton noTextBookButton = new JButton("非教科書");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(textBookButton);
		buttonPanel.add(noTextBookButton);

		soldPanel.add(buttonPanel, BorderLayout.NORTH);
		soldPanel.add(formPanel, BorderLayout.CENTER);

		textBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTextBookPanel();
			}
		});
		noTextBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNoTextBookPanel();
			}
		});

		soldPanel.revalidate();
		soldPanel.repaint();
	}

	private void submitBook() {

		String title = textField_1.getText();
		String author = textField_2.getText();
		String depreciation = textField_3.getText();
		String note;
		if (noteComboBox.getSelectedItem().equals("有")) {
			note = "有";
		} else {
			note = "無";
		}
		String condition = textField_4.getText();
		String price = textField_5.getText();

		if (select6.equals("教科書")) {
			String lesson = textField.getText();
			select1 = (String) collegeComboBox.getSelectedItem();
			select2 = (String) departmentComboBox.getSelectedItem();
			select3 = (String) yearComboBox.getSelectedItem();
			select4 = (String) courseComboBox.getSelectedItem();
			select5 = (String) typeComboBox.getSelectedItem();
			select5 = "";

			if (lesson.isEmpty() && title.isEmpty() || depreciation.isEmpty() || note.isEmpty() || condition.isEmpty()
					|| price.isEmpty() || selectedImagePath == null || select1 == null || select2 == null
					|| select3 == null || select4 == null) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields and upload an image", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {

				Book newBook = new Book(user.getID(), lesson, title, author, depreciation, note, condition, price,
						selectedImagePath, select1, select2, select3, select4, select5, select6);
				addBook(newBook);

				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				selectedImagePath = null;
				select1 = "";
				select2 = "";
				select3 = "";
				select4 = "";
				select5 = "";
				select6 = "";

				updateSoldPanel();
				soldPanel.revalidate();
				soldPanel.repaint();
			}
		} else if (select6.equals("非教科書")) {
			String lesson = "";
			select1 = "";
			select2 = "";
			select3 = "";
			select4 = "";
			select5 = (String) typeComboBox.getSelectedItem();
			if (title.isEmpty() || depreciation.isEmpty() || note.isEmpty() || condition.isEmpty() || price.isEmpty()
					|| selectedImagePath == null || select5 == null) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields and upload an image", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {

				Book newBook = new Book(user.getID(), lesson, title, author, depreciation, note, condition, price,
						selectedImagePath, select1, select2, select3, select4, select5, select6);
				addBook(newBook);

				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				selectedImagePath = null;
				select1 = "";
				select2 = "";
				select3 = "";
				select4 = "";
				select5 = "";
				select6 = "";

				updateSoldPanel();
				soldPanel.revalidate();
				soldPanel.repaint();
			}
		}

	}

	private void loadBooksFromDatabase() {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String query = "SELECT * FROM `FinalProject_Book1`";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ID");
				String lesson = rs.getString("lesson");
				String title = rs.getString("book");
				String author = rs.getString("author");
				String depreciation = rs.getString("depreciation");
				String note = rs.getString("note");
				String condition = rs.getString("condition");
				String price = rs.getString("price");
				String imagePath = rs.getString("picture");
				String college = rs.getString("college");
				String department = rs.getString("department");
				String grade = rs.getString("grade");
				String lessonType = rs.getString("lessonType");
				String bookType = rs.getString("bookType");
				String type = rs.getString("type");

				Book book = new Book(id, lesson, title, author, depreciation, note, condition, price, imagePath,
						college, department, grade, lessonType, bookType, type);
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 將使用者賣的東西集合成一個list
	public ArrayList<Book> getBooksById(int id) {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";
		ArrayList<Book> books = new ArrayList<>();

		String query = "SELECT * FROM `FinalProject_Book1` WHERE `ID` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String lesson = rs.getString("lesson");
				String title = rs.getString("book");
				String author = rs.getString("author");
				String depreciation = rs.getString("depreciation");
				String note = rs.getString("note");
				String condition = rs.getString("condition");
				String price = rs.getString("price");
				String imagePath = rs.getString("picture");
				String college = rs.getString("college");
				String department = rs.getString("department");
				String grade = rs.getString("grade");
				String lessonType = rs.getString("lessonType");
				String bookType = rs.getString("bookType");
				String type = rs.getString("type");

				Book book = new Book(id, lesson, title, author, depreciation, note, condition, price, imagePath,
						college, department, grade, lessonType, bookType, type);
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	// 更新逛一逛介面
	private void updateShopPanel(String string) {
		shopPanel.removeAll();

		// 創建下拉式選單並添加項目
		JComboBox<String> bookTypeComboBox = new JComboBox<>();
		bookTypeComboBox.addItem("全部");
		bookTypeComboBox.addItem("教科書");
		bookTypeComboBox.addItem("非教科書");
		// 監聽下拉式選單的選擇
		bookTypeComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedType = (String) bookTypeComboBox.getSelectedItem();
					// 根據選擇的類型更新商店面板
					if (selectedType.equals("教科書")) {
						updateShopPanel("教科書");
					} else if (selectedType.equals("非教科書")) {
						updateShopPanel("非教科書");
					}
				}
			}
		});

		// 添加下拉式選單到商店面板
		shopPanel.add(bookTypeComboBox, BorderLayout.NORTH);

		// 遍歷書籍列表，根據類型過濾書籍並添加到商店面板
		for (Book book : bookList) {
			if (string == null || string.equals("") || book.getType().equals(string)) {
				JPanel bookPanel = new JPanel();
				bookPanel.removeAll();
				bookPanel.setLayout(new BorderLayout());
				bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				JLabel bookImage = new JLabel(new ImageIcon(new ImageIcon(book.getImagePath()).getImage()
						.getScaledInstance(100, 150, Image.SCALE_DEFAULT)));
				bookPanel.add(bookImage, BorderLayout.WEST);

				JPanel bookInfoPanel = new JPanel();
				bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
				bookInfoPanel.add(new JLabel("賣家: " + book.getID()));
				bookInfoPanel.add(new JLabel("書名: " + book.getTitle()));
				bookInfoPanel.add(new JLabel("作者: " + book.getAuthor()));

				if (book.getType().equals("教科書")) {
					bookInfoPanel.add(new JLabel("學院: " + book.getCollege()));
				    bookInfoPanel.add(new JLabel("院系級: " + book.getDepartment() + book.getGrade()));
				    bookInfoPanel.add(new JLabel("課程類別: " + book.getLessonType()));
				    bookInfoPanel.add(new JLabel("課程: " + book.getLesson()));
				}
				bookInfoPanel.add(new JLabel("新舊: " + book.getDepreciation() + "成新"));
				bookInfoPanel.add(new JLabel("筆記: " + book.getNote()));
				bookInfoPanel.add(new JLabel("書況: " + book.getCondition()));
				bookInfoPanel.add(new JLabel("價錢: NT$" + book.getPrice()));

				JButton addToCartButton = new JButton("加入購物車");
				addToCartButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addBookToCart(book);
					}
				});

				bookInfoPanel.add(addToCartButton);
				bookPanel.add(bookInfoPanel, BorderLayout.CENTER);
				shopPanel.add(bookPanel);
			}
		}

		// 重新繪製商店面板
		shopPanel.revalidate();
		shopPanel.repaint();
	}

	private void updateCartPanel() {
		cartPanel.removeAll();
		cartPanel.add(new JLabel(user.getID() + "的購物車現有書籍:"));

		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";
		ArrayList<Book> books = new ArrayList<>();

		String query = "SELECT * FROM `FinalProject_Cart` WHERE `BuyerID` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, user.getID());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int sellerID = rs.getInt("SellerID");
				String title = rs.getString("book");
				String author = rs.getString("author");
				String depreciation = rs.getString("depreciation");
				String note = rs.getString("note");
				String condition = rs.getString("condition");
				String price = rs.getString("price");
				String imagePath = rs.getString("picture");

				JPanel bookPanel = new JPanel();
				bookPanel.setLayout(new BorderLayout());
				bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				JLabel bookImage = new JLabel(new ImageIcon(
						new ImageIcon(imagePath).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT))); // Book
																												// image
				bookPanel.add(bookImage, BorderLayout.WEST);

				JPanel bookInfoPanel = new JPanel();
				bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
				bookInfoPanel.add(new JLabel("賣家: " + sellerID));
				bookInfoPanel.add(new JLabel("書名: " + title));
				bookInfoPanel.add(new JLabel("作者: " + author));
				bookInfoPanel.add(new JLabel("新舊: " + depreciation +"成新"));
				bookInfoPanel.add(new JLabel("筆記: " + note));
				bookInfoPanel.add(new JLabel("書況: " + condition));
				bookInfoPanel.add(new JLabel("價錢: NT$" + price));

				JButton removeFromCartButton = new JButton("移出購物車");
				removeFromCartButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						removeFromCart(sellerID, title);
						updateCartPanel();
					}
				});

				JButton purchaseButton = new JButton("購買");
				purchaseButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buyFrame = new BuyFrame(sellerID, user, title, price, imagePath, HomeFrame.this);
						buyFrame.setVisible(true);
					}
				});

				bookInfoPanel.add(removeFromCartButton);
				bookInfoPanel.add(purchaseButton);
				bookPanel.add(bookInfoPanel, BorderLayout.CENTER);
				cartPanel.add(bookPanel);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cartPanel.revalidate();
		cartPanel.repaint();
	}

	// 加入賣場後的顯示資料
	private void updateSoldPanel() {
	    soldBooksPanel.removeAll();
	    soldBooksPanel.add(new JLabel("我的賣場現有書籍:"));

	    bookUserSold = getBooksById(user.getID());
	    for (Book book : bookUserSold) {
	        JPanel bookPanel = new JPanel();
	        bookPanel.setLayout(new BorderLayout());
	        bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	        JLabel bookImage = new JLabel(new ImageIcon(
	                new ImageIcon(book.getImagePath()).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT))); // 書本圖片
	        bookPanel.add(bookImage, BorderLayout.WEST);

	        JPanel bookInfoPanel = new JPanel();
	        bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
	        bookInfoPanel.add(new JLabel("書名: " + book.getTitle()));
	        bookInfoPanel.add(new JLabel("作者: " + book.getAuthor()));


	        if (!book.getType().equals("教科書")) {
	            bookInfoPanel.add(new JLabel("新舊: " + book.getDepreciation() + "成新"));
	            bookInfoPanel.add(new JLabel("筆記: " + book.getNote()));
	            bookInfoPanel.add(new JLabel("書況: " + book.getCondition()));
	        } else {
		        bookInfoPanel.add(new JLabel("學院: " + book.getCollege()));
	            bookInfoPanel.add(new JLabel("院系級: " + book.getDepartment() + book.getGrade()));
	            bookInfoPanel.add(new JLabel("課程類別: " + book.getLessonType()));
	            bookInfoPanel.add(new JLabel("課程: " + book.getLesson()));
	            bookInfoPanel.add(new JLabel("新舊: " + book.getDepreciation() + "成新"));
	            bookInfoPanel.add(new JLabel("筆記: " + book.getNote()));
	            bookInfoPanel.add(new JLabel("書況: " + book.getCondition()));
	        }

	        bookInfoPanel.add(new JLabel("價錢: NT$" + book.getPrice()));

	        JButton removeBookButton = new JButton("下架書籍");
	        removeBookButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                removeBookFromDatabase(book);
	                updateSoldPanel();
	                JOptionPane.showMessageDialog(null, "書本已下架", "Success", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	        bookInfoPanel.add(removeBookButton);

	        bookPanel.add(bookInfoPanel, BorderLayout.CENTER);
	        soldBooksPanel.add(bookPanel);
	    }

	    soldBooksPanel.revalidate();
	    soldBooksPanel.repaint();

	    this.soldPanel.add(soldBooksPanel, BorderLayout.SOUTH);
	}
	
	// 資料輸入至 DataBase
	private void insertData(Book book) {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String insertQuery = "INSERT INTO `FinalProject_Book1` (ID, lesson, book, author, depreciation, note, `condition`, price, picture, college, department, grade, lessonType, bookType, type) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insertQuery);
			pstmt.setLong(1, book.getID());
			pstmt.setString(2, book.getLesson());
			pstmt.setString(3, book.getTitle());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getDepreciation());
			pstmt.setString(6, book.getNote());
			pstmt.setString(7, book.getCondition());
			pstmt.setString(8, book.getPrice());
			pstmt.setString(9, book.getImagePath());
			pstmt.setString(10, book.getCollege());
			pstmt.setString(11, book.getDepartment());
			pstmt.setString(12, book.getGrade());
			pstmt.setString(13, book.getLessonType());
			pstmt.setString(14, book.getBookType());
			pstmt.setString(15, book.getType());

			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "資料已成功插入");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "資料插入失敗: " + e.getMessage());
		}
	}

	public static void showInfo(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			System.out.printf("%15s", metaData.getColumnLabel(i));
		}
		System.out.println();
	}

	// 下架書籍 刪除DataBase裡的資料
	private void removeBookFromDatabase(Book book) {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";

		String query = "DELETE FROM `FinalProject_Book1` WHERE `ID` = ? AND `book` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, book.getID());
			pstmt.setString(2, book.getTitle());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		removeFromCart(book.getID(), book.getTitle());
	}

	public void removeBookFromDatabase(int id, String title) {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";

		String query = "DELETE FROM `FinalProject_Book1` WHERE `ID` = ? AND `book` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, title);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		removeFromCart(id, title);
	}

	// 我的資料頁面
	private void setMyInfoPage() {
		infoPanel.removeAll();

		lblNewLabel = new JLabel("學號");
		lblNewLabel.setBounds(160, 55, 61, 16);
		infoPanel.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("聯絡方式");
		lblNewLabel_1.setBounds(160, 85, 61, 16);
		infoPanel.add(lblNewLabel_1);

		idLabel = new JLabel(Integer.toString(user.getID()));
		idLabel.setBounds(330, 55, 90, 16);
		infoPanel.add(idLabel);

		wtcLabel = new JLabel(user.getWtcPlatform() + "  " + user.getWtcName());
		wtcLabel.setBounds(330, 85, 180, 16);
		infoPanel.add(wtcLabel);

		logOutButton = new JButton("登出");
		logOutButton.setBounds(230, 198, 117, 29);
		infoPanel.add(logOutButton);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFrame startFrame = new StartFrame();
				startFrame.setVisible(true);
				setVisible(false);
			}
		});

	}

	// SoldPanel新增書籍用
	public void addBook(Book newBook) {
		bookList.add(newBook);
		updateSoldPanel();
		insertData(newBook);
	}

	public void addBookToCart(Book newBook) {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";
		Book book = newBook;

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String insertQuery = "INSERT INTO `FinalProject_Cart` (BuyerID, SellerID, lesson, book, author, depreciation, note, `condition`, price, picture) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insertQuery);
			pstmt.setLong(1, user.getID());
			pstmt.setLong(2, book.getID());
			pstmt.setString(3, book.getLesson());
			pstmt.setString(4, book.getTitle());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getDepreciation());
			pstmt.setString(7, book.getNote());
			pstmt.setString(8, book.getCondition());
			pstmt.setString(9, book.getPrice());
			pstmt.setString(10, book.getImagePath());

			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "書本已加入購物車", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "加入購物車失敗: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void removeFromCart(int sellerID, String title) {
		int id = sellerID;
		String book = title;
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";

		String query = "DELETE FROM `FinalProject_Cart` WHERE `SellerID` = ? AND `book` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, book);

			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "書本已從購物車移出", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "移出購物車失敗: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void BuyPage() {
		dealPanel.removeAll();

		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "112306070";
		String url = server + database + "?useSSL=false";
		String username = "112306070";
		String password = "dqocn";
		ArrayList<Book> books = new ArrayList<>();

		String query = "SELECT * FROM `FinalProject_Buy` WHERE `SellerID` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, user.getID());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String buyerID = rs.getString("BuyerID");
				String title = rs.getString("BookTitle");
				String price = rs.getString("price");
				String imagePath = rs.getString("Image");
				String timeAndLocation = rs.getString("TimeAndLocation");

				JPanel bookPanel = new JPanel();
				bookPanel.setLayout(new BorderLayout());
				bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				JLabel bookImage = new JLabel(new ImageIcon(
						new ImageIcon(imagePath).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT))); // Book
																												// image
				bookPanel.add(bookImage, BorderLayout.WEST);

				JPanel bookInfoPanel = new JPanel();
				bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
				bookInfoPanel.add(new JLabel("買家學號    :   " + buyerID));
				bookInfoPanel.add(new JLabel("書名        :   " + title));
				bookInfoPanel.add(new JLabel("價錢        :   " + price));
				bookInfoPanel.add(new JLabel("面交時間與地點:   " + timeAndLocation));

				bookPanel.add(bookInfoPanel, BorderLayout.CENTER);
				dealPanel.add(bookPanel);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "SELECT * FROM `FinalProject_Buy` WHERE `BuyerID` = ?";
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, user.getID());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String sellerID = rs.getString("SellerID");
				String title = rs.getString("BookTitle");
				String price = rs.getString("price");
				String imagePath = rs.getString("Image");
				String timeAndLocation = rs.getString("TimeAndLocation");

				JPanel bookPanel = new JPanel();
				bookPanel.setLayout(new BorderLayout());
				bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				JLabel bookImage = new JLabel(new ImageIcon(
						new ImageIcon(imagePath).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT))); // Book
																												// image
				bookPanel.add(bookImage, BorderLayout.WEST);

				JPanel bookInfoPanel = new JPanel();
				bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
				bookInfoPanel.add(new JLabel("賣家學號    :   " + sellerID));
				bookInfoPanel.add(new JLabel("書名        :   " + title));
				bookInfoPanel.add(new JLabel("價錢        :   " + price));
				bookInfoPanel.add(new JLabel("面交時間與地點:   " + timeAndLocation));

				bookPanel.add(bookInfoPanel, BorderLayout.CENTER);
				dealPanel.add(bookPanel);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			dealPanel.revalidate();
			dealPanel.repaint();
		}
	}
}