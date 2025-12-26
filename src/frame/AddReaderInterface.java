package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dao.ReaderDao;
import model.Reader;
import util.Connect;
import util.StringNull;
import util.UIStyle;

// AddReaderInterface 类定义了用于添加新用户的界面，它是一个模态对话框，继承自 JDialog。
// 该类使用了 javax.swing 包中的各种 UI 组件，如 JDialog, JPanel, JLabel, JTextField, JButton, JOptionPane。
// 它还使用了 dao.ReaderDao 处理用户数据访问，model.Reader 定义用户数据模型，以及 util.Connect, util.StringNull, util.UIStyle 等实用工具类。
public class AddReaderInterface extends JDialog {

	// 序列化版本UID，用于版本控制。
	private static final long serialVersionUID = -7230175691567354083L;
	// contentPane 对象，JPanel 类型，是对话框的主要内容面板。
	private JPanel contentPane;
	// lblNewLabel_1 对象，JLabel 类型，显示文本"用户名："。
	private final JLabel lblNewLabel_1 = new JLabel("用户名：");
	// textField_1 对象，JTextField 类型，用于输入用户名。
	private final JTextField textField_1 = new JTextField();
	// lblNewLabel_1_1_1 对象，JLabel 类型，显示文本"用户密码："。
	private final JLabel lblNewLabel_1_1_1 = new JLabel("用户密码：");
	// textField_3 对象，JTextField 类型，用于输入用户密码。
	private final JTextField textField_3 = new JTextField();

	// btnNewButton 对象，JButton 类型，用于触发表单提交（添加用户）操作。
	private final JButton btnNewButton = new JButton("添加");
	// btnNewButton_1 对象，JButton 类型，用于触发清空表单操作。
	private final JButton btnNewButton_1 = new JButton("清空");
	// lblNewLabel_2 对象，JLabel 类型，显示文本"请输入要添加的用户信息："，作为标题。
	private final JLabel lblNewLabel_2 = new JLabel("请输入要添加的用户信息：");
	// label 对象，JLabel 类型，一个空的标签，可能用于背景或布局。
	private final JLabel label = new JLabel("");
	// conutil 对象，Connect 类型，用于管理数据库连接。
	private Connect conutil = new Connect();
	// readerDao 对象，ReaderDao 类型，用于执行与读者相关的数据库操作。
	private ReaderDao readerDao = new ReaderDao();

	// AddReaderInterface 类的构造方法。
	// 参数 parent 是父窗口 Frame，title 是对话框标题，modal 是布尔值表示是否为模态对话框。
	// 该方法负责初始化对话框的各项属性、布局UI组件、设置监听器以及应用样式。
	public AddReaderInterface(Frame parent) {
		// 调用父类 JDialog 的构造方法，设置父窗口、标题和模态性。
		super(parent, "用户添加", true);
		// 设置对话框不可调整大小。
		setResizable(false);

		// 设置对话框关闭时的默认操作为销毁。
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// 设置对话框的位置和大小。
		setBounds(100, 100, 455, 300);
		// 创建内容面板。
		contentPane = new JPanel();
		// 应用卡片式UI样式到内容面板。
		UIStyle.applyCardStyle(contentPane);
		// 将内容面板设置到对话框中。
		setContentPane(contentPane);
		// 设置内容面板的布局为绝对布局。
		contentPane.setLayout(null);

		// 设置 label 组件的位置和大小。
		label.setBounds(19, 16, 395, 289);
		// 移除 label 的边框。
		label.setBorder(null);
		// 将 label 添加到内容面板。
		contentPane.add(this.label);



		// 设置用户名标签的位置和大小。
		this.lblNewLabel_1.setBounds(41, 84, 60, 18);
		// 设置用户名标签的字体。
		this.lblNewLabel_1.setFont(UIStyle.FONT_NORMAL);
		// 设置用户名标签的前景色。
		this.lblNewLabel_1.setForeground(UIStyle.FONT_COLOR_MAIN);
		// 将用户名标签添加到内容面板。
		contentPane.add(this.lblNewLabel_1);
		// 设置用户名输入框的列数。
		this.textField_1.setColumns(10);
		// 设置用户名输入框的位置和大小。
		this.textField_1.setBounds(113, 78, 250, 30);
		// 设置用户名输入框的字体。
		this.textField_1.setFont(UIStyle.FONT_NORMAL);
		// 将用户名输入框添加到内容面板。
		contentPane.add(this.textField_1);

		// 设置用户密码标签的位置和大小。
		this.lblNewLabel_1_1_1.setBounds(41, 140, 72, 18);
		// 设置用户密码标签的字体。
		this.lblNewLabel_1_1_1.setFont(UIStyle.FONT_NORMAL);
		// 设置用户密码标签的前景色。
		this.lblNewLabel_1_1_1.setForeground(UIStyle.FONT_COLOR_MAIN);
		// 将用户密码标签添加到内容面板。
		contentPane.add(this.lblNewLabel_1_1_1);
		// 设置用户密码输入框的列数。
		this.textField_3.setColumns(10);
		// 设置用户密码输入框的位置和大小。
		this.textField_3.setBounds(113, 134, 250, 30);
		// 设置用户密码输入框的字体。
		this.textField_3.setFont(UIStyle.FONT_NORMAL);
		// 将用户密码输入框添加到内容面板。
		contentPane.add(this.textField_3);

		// 为"添加"按钮添加动作监听器。
		// 当按钮被点击时，执行 addReader() 方法。
		this.btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addReader();
			}
		});

		// 设置"添加"按钮的位置和大小。
		this.btnNewButton.setBounds(71, 200, 90, 30);
		// 应用自定义样式到"添加"按钮。
		styleButton(btnNewButton);
		// 将"添加"按钮添加到内容面板。
		contentPane.add(this.btnNewButton);

		// 为"清空"按钮添加动作监听器。
		// 当按钮被点击时，执行 delActiontxt() 方法。
		this.btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delActiontxt();
			}
		});
		// 设置"清空"按钮的位置和大小。
		this.btnNewButton_1.setBounds(273, 200, 90, 30);
		// 应用自定义样式到"清空"按钮。
		styleButton(btnNewButton_1);
		// 将"清空"按钮添加到内容面板。
		contentPane.add(this.btnNewButton_1);

		// 设置标题标签的字体。
		this.lblNewLabel_2.setFont(UIStyle.FONT_TITLE);
		// 设置标题标签的前景色。
		        this.lblNewLabel_2.setForeground(UIStyle.BUTTON_PRIMARY);
		// 设置标题标签的位置和大小。
		this.lblNewLabel_2.setBounds(38, 37, 300, 29);
		// 将标题标签添加到内容面板。
		contentPane.add(this.lblNewLabel_2);
		// 将对话框居中显示在父窗口。
		setLocationRelativeTo(parent);
	}

	// styleButton 方法，用于为 JButton 应用统一的UI样式。
	// 参数 button 是需要应用样式的 JButton 对象。
	// 该方法设置按钮的字体、背景色、前景色、焦点绘制和边框。
	private void styleButton(JButton button) {
		button.setFont(UIStyle.FONT_BUTTON);
		button.setBackground(UIStyle.BUTTON_PRIMARY);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	}

	// addReader 方法，处理添加读者信息的业务逻辑。
	// 该方法从输入框获取用户输入，进行数据校验，然后调用 ReaderDao 将读者信息添加到数据库。
	// 过程中会使用到 StringNull 工具类进行空值判断，JOptionPane 显示提示信息，Connect 工具类管理数据库连接，以及 ReaderDao 进行数据库操作。
	protected void addReader() {
		// 从用户名输入框获取文本，并将其转换为字符串。
		String readerName = this.textField_1.getText();
		// 从用户密码输入框获取文本，并将其转换为字符串。
		String readerPass = this.textField_3.getText();

		// 校验用户名是否为空。如果为空，则显示错误信息并返回。
		if (StringNull.isEmpty(readerName)) {
			JOptionPane.showMessageDialog(this, "用户名不能为空！");
			return;
		}
		// 校验用户密码是否为空。如果为空，则显示错误信息并返回。
		if (StringNull.isEmpty(readerPass)) {
			JOptionPane.showMessageDialog(this, "用户密码不能为空！");
			return;
		}
		// 声明一个 Connection 对象，用于数据库连接。
		Connection con = null;
		try {
			// 通过 Connect 工具类获取数据库连接。
			con = conutil.loding();
			
			// 检查用户名是否已存在于数据库中
			String checkSql = "SELECT COUNT(*) FROM reader WHERE reader_name = ?";
			java.sql.PreparedStatement checkStmt = con.prepareStatement(checkSql);
			checkStmt.setString(1, readerName);
			java.sql.ResultSet rs = checkStmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				JOptionPane.showMessageDialog(this, "添加失败，此用户名已存在！");
				checkStmt.close();
				return;
			}
			checkStmt.close();
			
			// 创建一个 Reader 对象，封装用户输入的信息。
			// 使用 Reader 类的构造函数，用户编号将由数据库自动递增。
			Reader reader = new Reader(readerName, readerPass);
			// 调用 readerDao 的 register 方法，将新的读者信息添加到数据库。
			int result = readerDao.register(con, reader);
			// 根据数据库操作的结果，判断是否添加成功。
			if (result > 0) {
				// 如果添加成功，则显示成功信息并关闭当前对话框。
				JOptionPane.showMessageDialog(this, "添加成功！");
				dispose();
			} else {
				// 如果添加失败，则显示数据库操作未成功的错误信息。
				JOptionPane.showMessageDialog(this, "添加失败，数据库操作未成功！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// 捕获所有异常并打印堆栈信息。
			e.printStackTrace();
			// 显示未知错误信息。
			JOptionPane.showMessageDialog(this, "添加失败，发生未知错误！", "错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			// 在 finally 块中确保数据库连接被关闭，即使发生异常。
			try {
				// 检查连接是否非空，然后关闭连接。
				// 调用 conutil 的 closeCon 方法。
				if(con != null) conutil.closeCon(con);
			} catch (Exception e) {
				// 捕获关闭连接时可能发生的异常并打印堆栈信息。
				e.printStackTrace();
			}
		}
	}

	// delActiontxt 方法，用于清空所有文本输入框的内容。
	// 该方法直接设置 JTextFields 的文本为空字符串。
	private void delActiontxt() {
		this.textField_1.setText("");
		this.textField_3.setText("");
	}
}
