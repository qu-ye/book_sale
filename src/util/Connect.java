package util;

import java.sql.Connection;
import java.sql.DriverManager;


public class Connect {
	// 属性: con
	// 类型: Connection
	// 作用: 存储数据库连接对象。
	Connection con;// 数据库连接
	
	static final String jdbcUrl = "jdbc:mysql://localhost:3306/lsms_db?useSSL=false&serverTimezone=UTC";
	
	static final String jdbcUsername = "root"; // 数据库账户名

	static final String jdbcPassword = "123456"; // 数据库账户名的密码

	public Connection loding() {// 加载数据库
		try {
			// 加载MySQL JDBC驱动类
			Class.forName("com.mysql.cj.jdbc.Driver");// 加载驱动 mysql 8.0以上版本
			System.out.println("成功加载数据库驱动！");
		} catch (Exception e) {
			// 捕获并处理加载驱动时发生的异常
			System.out.println("加载数据库驱动出错！");
			e.printStackTrace();// 在命令行打印异常信息在程序中出错的位置及原因
		}
		// 连接数据库
		try {
			// 使用DriverManager获取数据库连接
			con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
			System.out.println("成功连接数据库服务器！！");
		} catch (Exception e1) {
			// 捕获并处理连接数据库时发生的异常
			System.out.println("连接数据库服务器出现错误！！");
		}

		// 返回建立的数据库连接对象
		return con;
	}

	public void closeCon(java.sql.Connection con) throws Exception {// 关闭数据库
		// 检查连接对象是否为null，如果不是则关闭连接
		if (con != null) {
			con.close();
		}
	}
}
