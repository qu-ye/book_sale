package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import dao.ReaderDao;
import util.Connect;


public class DeleteselfInterface extends JFrame {

	private static final long serialVersionUID = -1878106319697766203L;
	// contentPane 对象，JPanel 类型，是窗口的主内容面板。
	private JPanel contentPane;
	// lblNewLabel 对象，JLabel 类型，显示欢迎语和提示信息。
	private final JLabel lblNewLabel = new JLabel("尊敬的用户您好，您正在执行用户注销操作");
	// btnNewButton 对象，JButton 类型，用于触发确认注销操作的按钮。
	private final JButton btnNewButton = new JButton("确认注销");
	// lblNewLabel_2 对象，JLabel 类型，显示"警告：此操作不可逆！"的警告信息。
	private final JLabel lblNewLabel_2 = new JLabel("警告：此操作不可逆！");
	// conutil 对象，Connect 类型，用于管理数据库连接。
	private Connect conutil = new Connect();
	// readerDao 对象，ReaderDao 类型，用于执行与读者相关的数据库操作。
	private ReaderDao readerDao = new ReaderDao();
	// lblNewLabel_1 对象，JLabel 类型，显示"请确认您的用户编号："的提示文本。
	private final JLabel lblNewLabel_1 = new JLabel("请确认您的用户编号：");
	// lblNewLabel_1_1 对象，JLabel 类型，显示当前登录的用户ID。
	private final JLabel lblNewLabel_1_1 = new JLabel("-1");


	public DeleteselfInterface() {
		// 设置窗口不可调整大小。
		setResizable(false);
		// 设置窗口标题。
		setTitle("用户注销");
		// 设置窗口关闭操作为只关闭当前窗口。
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// 设置窗口的位置和大小。
		setBounds(100, 100, 455, 255);
		// 初始化主内容面板。
		contentPane = new JPanel();
		// 设置内容面板的工具提示文本。
		contentPane.setToolTipText("");
		// 设置内容面板的边框。
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 将主内容面板设置为当前 JFrame 的内容面板。
		setContentPane(contentPane);
		// 设置内容面板的布局为绝对布局（null layout）。
		contentPane.setLayout(null);
		// 设置窗口居中显示。
		setLocationRelativeTo(null);
		// 设置 lblNewLabel 组件的字体和位置大小。
		this.lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		this.lblNewLabel.setBounds(7, 6, 380, 29);

		// 将 lblNewLabel 组件添加到内容面板。
		contentPane.add(this.lblNewLabel);
		// 为 btnNewButton 添加动作监听器，点击时调用 deleteSelf() 方法。
		this.btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelf();
			}
		});
		// 设置 btnNewButton 的位置大小。
		this.btnNewButton.setBounds(105, 142, 151, 60);

		// 将 btnNewButton 组件添加到内容面板。
		contentPane.add(this.btnNewButton);
		// 设置 lblNewLabel_2 组件的前景色（红色）、字体和位置大小。
		this.lblNewLabel_2.setForeground(Color.RED);
		this.lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 18));
		this.lblNewLabel_2.setBounds(7, 96, 353, 26);

		// 将 lblNewLabel_2 组件添加到内容面板。
		contentPane.add(this.lblNewLabel_2);
		// 设置 lblNewLabel_1 组件的字体和位置大小。
		this.lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		this.lblNewLabel_1.setBounds(7, 47, 200, 29);

		// 将 lblNewLabel_1 组件添加到内容面板。
		contentPane.add(this.lblNewLabel_1);
		// 设置 lblNewLabel_1_1 组件的字体和位置大小。
		this.lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		this.lblNewLabel_1_1.setBounds(219, 47, 200, 29);

		// 将 lblNewLabel_1_1 组件添加到内容面板。
		contentPane.add(this.lblNewLabel_1_1);
		// 设置 lblNewLabel_1_1 的文本为当前登录的用户ID。
		lblNewLabel_1_1.setText(String.valueOf(EnterInterface.readerid));
	}


	protected void deleteSelf() {
		// 获取当前用户的读者ID（从标签中获取）。
		String ReaderId = this.lblNewLabel_1_1.getText();
		// 声明数据库连接对象。
		Connection con = null;
		try {
			// 获取数据库连接。
			con = conutil.loding();
			// 通过 readerDao 查询读者是否存在。
			ResultSet rs = readerDao.queryById(con, Integer.parseInt(ReaderId));
			// 如果查询结果存在（即找到了该读者）：
			if (rs.next()) {
				// 执行读者删除操作。
				readerDao.delete(con, Integer.parseInt(ReaderId));
				JOptionPane.showMessageDialog(null, "注销账号成功，欢迎再次使用。");
				System.exit(0);// 完全退出整个程序。
				return;
			} else {
				// 如果未找到该编号的读者，则显示注销失败消息。
				JOptionPane.showMessageDialog(null, "注销失败！未找到该编号用户！");
				return;
			}

		} catch (Exception e) {
			// 捕获异常并打印堆栈信息。
			e.printStackTrace();
			// 显示注销失败的错误消息（未知错误）。
			JOptionPane.showMessageDialog(null, "注销失败！未知错误！");
			return;
		} finally {
			try {
				// 在 finally 块中确保数据库连接被关闭。
				conutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
