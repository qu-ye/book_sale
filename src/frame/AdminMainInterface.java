package frame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import util.UIStyle;

/**
 * 管理员主界面 - 新版
 * 该类继承自 JFrame，是应用程序的主窗口，用于管理员登录后的操作界面。
 * 主要功能包括：显示管理员欢迎信息、导航菜单（图书管理、用户管理、订单管理）以及各个功能面板的切换。
 * 使用了 Swing 组件 JFrame, JPanel, JLabel, JButton, CardLayout, BoxLayout, BorderLayout, JOptionPane 等。
 * 还使用了 AWT 中的 Color, Dimension, Cursor 等。
 * 引用了 EnterInterface 类来获取登录信息并在登录无效时返回登录界面。
 * 引用了 BookManagementPanel, UserManagementPanel, OrderManagementPanel 等功能面板类。
 */
public class AdminMainInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	// 界面常量 (与ReaderMainInterface保持一致)
	private static final int WINDOW_WIDTH = 1500;
	private static final int WINDOW_HEIGHT = 950;

	private JPanel contentPane;
	private JPanel navPanel;
	private JPanel cardPanel;
	private CardLayout cardLayout;

	private String adminName;

	// 导航按钮
	private JButton[] navButtons;
	private String[] navTitles = {"图书管理", "用户管理", "订单管理"};
	private String[] cardNames = {"BOOK_MANAGE", "USER_MANAGE", "ORDER_MANAGE"};
	    private Color defaultNavColor = UIStyle.BUTTON_PRIMARY;
	private Color activeNavColor = UIStyle.HOVER_COLOR;

	/**
	 * Create the frame.
	 * AdminMainInterface 类的构造方法。
	 * 该方法在创建管理员主界面时被调用，主要负责初始化管理员名称、设置窗口的基本属性，
	 * 并调用 initComponents() 方法来构建和布局UI组件。
	 * 如果管理员名称无效，则会弹出错误信息并跳转回登录界面。
	 */
	public AdminMainInterface() {
		this.adminName = EnterInterface.adminname;

		if (adminName == null || adminName.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "登录信息无效，请重新登录！", "错误", JOptionPane.ERROR_MESSAGE);
			new EnterInterface().setVisible(true);
			dispose();
			return;
		}

		// 基本窗口设置
		setTitle("图书销售管理系统 - 管理员后台");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(true); // 设为可拉伸以适应不同屏幕

		// 初始化UI
		initComponents();
	}

	private void initComponents() {
		contentPane = new JPanel(new BorderLayout(0, 0));
		contentPane.setBackground(UIStyle.MAIN_BACKGROUND);
		setContentPane(contentPane);

		// 创建顶部面板
		contentPane.add(createTopPanel(), BorderLayout.NORTH);

		// 创建左侧导航面板
		navPanel = createNavPanel();
		contentPane.add(navPanel, BorderLayout.WEST);

		// 创建卡片面板
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.setBackground(UIStyle.MAIN_BACKGROUND);
		contentPane.add(cardPanel, BorderLayout.CENTER);

		// 添加各个功能面板
		cardPanel.add(createBookManagementPanel(), cardNames[0]);
		cardPanel.add(createUserManagementPanel(), cardNames[1]);
		cardPanel.add(createOrderManagementPanel(), cardNames[2]);

		// 默认显示第一个面板
		cardLayout.show(cardPanel, cardNames[0]);
	}

	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(UIStyle.BUTTON_PRIMARY);
		panel.setPreferredSize(new Dimension(WINDOW_WIDTH, 80));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyle.BORDER_COLOR),
				new EmptyBorder(10, 20, 10, 20)
		));

		JLabel titleLabel = new JLabel("图书销售管理系统 - 后台管理");
		titleLabel.setFont(UIStyle.FONT_TITLE);
		titleLabel.setForeground(Color.WHITE);
		panel.add(titleLabel, BorderLayout.WEST);

		// 创建右侧面板，包含欢迎信息和切换登录按钮
		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		rightPanel.setOpaque(false);

		JLabel welcomeLabel = new JLabel("欢迎您，管理员 " + adminName);
		welcomeLabel.setFont(UIStyle.FONT_NORMAL);
		welcomeLabel.setForeground(Color.WHITE);
		rightPanel.add(welcomeLabel);

		// 添加切换登录按钮
		JButton switchLoginButton = new JButton("切换登录");
		switchLoginButton.setFont(UIStyle.FONT_BUTTON);
		switchLoginButton.setForeground(Color.WHITE);
		switchLoginButton.setBackground(UIStyle.HOVER_COLOR);
		switchLoginButton.setBorderPainted(false);
		switchLoginButton.setFocusPainted(false);
		switchLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		switchLoginButton.addActionListener(e -> {
			dispose();
			new EnterInterface().setVisible(true);
		});
		rightPanel.add(switchLoginButton);

		panel.add(rightPanel, BorderLayout.EAST);
		return panel;
	}

	private JPanel createNavPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(UIStyle.BUTTON_PRIMARY);
		panel.setPreferredSize(new Dimension(200, 0));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, UIStyle.BORDER_COLOR),
				new EmptyBorder(20, 10, 20, 10)
		));

		// 添加Logo或标题
        JLabel navTitle = new JLabel("导航菜单");
        navTitle.setFont(UIStyle.FONT_TITLE.deriveFont(20f));
        navTitle.setForeground(Color.WHITE);
        navTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        navTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(navTitle);


		navButtons = new JButton[navTitles.length];
		for (int i = 0; i < navTitles.length; i++) {
			navButtons[i] = createNavButton(navTitles[i], i);
			panel.add(navButtons[i]);
			panel.add(Box.createVerticalStrut(15));
		}
		
		panel.add(Box.createVerticalGlue()); // 使得按钮靠上

		// 设置第一个按钮为激活状态
		navButtons[0].setBackground(activeNavColor);
		return panel;
	}

	private JButton createNavButton(String title, int index) {
		JButton button = new JButton(title);
		button.setFont(UIStyle.FONT_BUTTON);
		button.setForeground(Color.WHITE);
		button.setBackground(defaultNavColor);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(180, 50));
		button.setMaximumSize(new Dimension(180, 50));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (JButton b : navButtons) {
					b.setBackground(defaultNavColor);
				}
				button.setBackground(activeNavColor);
				cardLayout.show(cardPanel, cardNames[index]);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (button.getBackground() != activeNavColor) {
					button.setBackground(UIStyle.HOVER_COLOR);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (button.getBackground() != activeNavColor) {
					button.setBackground(defaultNavColor);
				}
			}
		});
		return button;
	}

	// --- 面板创建方法 (占位符) ---
	private JPanel createBookManagementPanel() {
        return new BookManagementPanel();
	}

	private JPanel createUserManagementPanel() {
		return new UserManagementPanel();
	}

	private JPanel createOrderManagementPanel() {
		return new OrderManagementPanel();
	}

	public String getAdminname() {
		return adminName;
	}

	public void setAdminname(String adminname) {
		this.adminName = adminname;
		// 如果需要，可以在这里更新界面上的欢迎信息
	}
}
