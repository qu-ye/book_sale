package util;

import java.awt.EventQueue;
import java.io.File;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import frame.EnterInterface;
import dao.BookDao;
import model.Book;


public class Main {

	// 静态初始化块
	// 作用: 在类加载时执行，用于设置系统属性以解决Java Swing中可能出现的sRGB配置文件错误。
	static {
		// 解决 javax.imageio.IIOException: Incorrect sRGB profile 错误，禁用CodecLib
		System.setProperty("com.sun.media.imageio.disableCodecLib", "true");
	}


	public static void main(String[] args) {
		// 应用程序启动前，清理img/book_cover目录下未被数据库引用的图片
		cleanUnusedBookCovers();
		// 使用EventQueue.invokeLater确保Swing UI更新在事件调度线程中进行
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					// 设置Swing界面的外观为NimbusLookAndFeel，以提供现代化的UI风格
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					// 创建并显示EnterInterface（登录界面）实例
					EnterInterface frame = new EnterInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					// 打印UI初始化过程中发生的任何异常堆栈信息
					e.printStackTrace();
				}
			}
		});
	}

	
	private static void cleanUnusedBookCovers() {
		try (Connection con = new util.Connect().loding()) { // 尝试获取数据库连接，使用try-with-resources确保自动关闭
			BookDao bookDao = new BookDao(); // 实例化BookDao，用于数据库操作
			Set<String> usedCovers = new HashSet<>(); // 创建HashSet存储数据库中引用的封面文件名，用于快速查找
			// 遍历数据库中所有的图书记录，收集被引用的封面路径
			for (Book book : bookDao.queryAll(con)) {
				String cover = book.getBook_cover(); // 获取图书的封面路径
				if (cover != null && !cover.trim().isEmpty()) {
					// 只保留文件名部分
					String fileName = cover.replace("\\", "/");
					int idx = fileName.lastIndexOf("/");
					if (idx != -1) fileName = fileName.substring(idx + 1);
					usedCovers.add(fileName);
				}
			}
			System.out.println("[图片清理] 数据库引用的封面文件名：" + usedCovers);
			File coverDir = new File("img/book_cover");
			if (coverDir.exists() && coverDir.isDirectory()) {
				File[] files = coverDir.listFiles();
				if (files != null) {
					for (File file : files) {
						if (file.isFile()) {
							if (!usedCovers.contains(file.getName())) {
								System.out.println("[图片清理] 删除未引用图片：" + file.getName());
								boolean deleted = file.delete();
								if (!deleted) {
									System.err.println("[图片清理] 删除失败：" + file.getAbsolutePath());
								}
							} else {
								System.out.println("[图片清理] 保留图片：" + file.getName());
							}
						}
					}
				}
			} else {
				System.err.println("[图片清理] 目录不存在：" + coverDir.getAbsolutePath());
			}
		} catch (Exception e) {
			System.err.println("[警告] 清理封面图片时出错：" + e.getMessage());
			e.printStackTrace();
		}
	}

}
