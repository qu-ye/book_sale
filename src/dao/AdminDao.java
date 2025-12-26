package dao;

// 定义包，用于组织相关的类。此文件属于 `dao` 包，表示它是一个数据访问对象层。
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Admin;

public class AdminDao {

	
	public Admin login(Connection con, Admin admin) throws Exception {// 登录查找信息
		Admin resultUser = null;
		String sql = "select * from admin where admin_name=? and admin_password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, admin.getAdmin_name());// 将指定参数设置为给定 Java String 值
		pstmt.setString(2, admin.getAdmin_password());
		ResultSet rs = pstmt.executeQuery();// 在此 PreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
		if (rs.next()) {
			resultUser = new Admin();
			resultUser.setAdmin_id(rs.getInt("admin_id"));
			resultUser.setAdmin_name(rs.getString("admin_name"));
//			resultUser.setAdmin_phone(rs.getString("admin_phone"));
			resultUser.setAdmin_password(rs.getString("admin_password"));
		}
		return resultUser;
	}

	
	public Admin getAdminById(Connection con, int adminId) throws Exception {
		Admin resultUser = null;
		String sql = "select * from admin where admin_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, adminId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new Admin();
			resultUser.setAdmin_id(rs.getInt("admin_id"));
			resultUser.setAdmin_name(rs.getString("admin_name"));
			resultUser.setAdmin_phone(rs.getString("admin_phone"));
			resultUser.setAdmin_password(rs.getString("admin_password"));
		}
		return resultUser;
	}

	
	public int add(Connection con, Admin admin) throws Exception {
		String sql = "insert into admin(admin_id, admin_name, admin_phone, admin_password) values(?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, admin.getAdmin_id());
		pstmt.setString(2, admin.getAdmin_name());
		pstmt.setString(3, admin.getAdmin_phone());
		pstmt.setString(4, admin.getAdmin_password());
		return pstmt.executeUpdate();
	}


	public int register(Connection con, Admin admin) throws Exception {
		// 先获取下一个可用的ID
		String maxIdSql = "select max(admin_id) from admin";
		PreparedStatement maxIdStmt = con.prepareStatement(maxIdSql);
		ResultSet rs = maxIdStmt.executeQuery();
		int nextId = 1;
		if (rs.next()) {
			nextId = rs.getInt(1) + 1;
		}
		maxIdStmt.close();
		
		// 插入新管理员，提供必需的字段
		String sql = "insert into admin(admin_id, admin_name, admin_phone, admin_password) values(?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, nextId);
		pstmt.setString(2, admin.getAdmin_name());
		pstmt.setString(3, "未设置"); // 默认手机号
		pstmt.setString(4, admin.getAdmin_password());
		return pstmt.executeUpdate();
	}

	
	public boolean isUsernameExists(Connection con, String adminName) throws Exception {
		String sql = "select count(*) from admin where admin_name = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, adminName);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1) > 0;
		}
		return false;
	}

	
	public ResultSet query(Connection con, String adminName) throws Exception {
		String sql = "SELECT * FROM admin WHERE admin_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, adminName);
		return pstmt.executeQuery();
	}
}
