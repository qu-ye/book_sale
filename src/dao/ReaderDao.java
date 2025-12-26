package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Reader;


public class ReaderDao {

	public int register(Connection con, Reader reader) throws Exception {
		// 先获取下一个可用的ID
		String maxIdSql = "select max(reader_id) from reader";
		PreparedStatement maxIdStmt = con.prepareStatement(maxIdSql);
		ResultSet rs = maxIdStmt.executeQuery();
		int nextId = 1;
		if (rs.next()) {
			nextId = rs.getInt(1) + 1;
		}
		maxIdStmt.close();
		
		// 插入新用户，提供必需的字段
		String sql = "insert into reader(reader_id, reader_name, reader_phone, reader_password) values(?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, nextId);
		pstmt.setString(2, reader.getReader_name());
		pstmt.setString(3, "未设置"); // 默认手机号
		pstmt.setString(4, reader.getReader_password());
		return pstmt.executeUpdate();
	}


	public Reader login(Connection con, Reader reader) throws Exception {
		Reader resultUser = null;
		String sql = "select * from reader where reader_name=? and reader_password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, reader.getReader_name());
		pstmt.setString(2, reader.getReader_password());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new Reader();
			resultUser.setReader_id(rs.getInt("reader_id"));
			resultUser.setReader_name(rs.getString("reader_name"));
			resultUser.setReader_phone(rs.getString("reader_phone"));
			resultUser.setReader_password(rs.getString("reader_password"));
		}
		return resultUser;
	}





	public boolean isUsernameExists(Connection con, String readerName) throws Exception {
		String sql = "select count(*) from reader where reader_name = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, readerName);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1) > 0;
		}
		return false;
	}

	



	public int delete(Connection con, int readerId) throws Exception {
		String sql = "delete from reader where reader_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, readerId);
		return pstmt.executeUpdate();
	}


	public ResultSet queryall(Connection con) throws SQLException {
		String sql = new String("select * from reader");
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}


	public int update(Connection con, Reader reader) throws Exception {
		String sql = "UPDATE reader SET reader_name=?, reader_phone=?, reader_password=?, " +
					 "reader_nickname=?, reader_gender=?, reader_email=?, reader_address=? " +
					 "WHERE reader_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, reader.getReader_name());
		pstmt.setString(2, reader.getReader_phone());
		pstmt.setString(3, reader.getReader_password());
		pstmt.setString(4, reader.getReader_nickname());
		pstmt.setString(5, reader.getReader_gender());
		pstmt.setString(6, reader.getReader_email());
		pstmt.setString(7, reader.getReader_address());
		pstmt.setInt(8, reader.getReader_id());
		return pstmt.executeUpdate();
	}


	public ResultSet queryOrders(Connection con, int readerId) throws Exception {
		String sql = "SELECT o.*, b.book_name FROM book_order o " +
					"JOIN book b ON o.order_book_id = b.book_id " +
					"WHERE o.order_reader_id = ? " +
					"ORDER BY o.order_time DESC";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, readerId);
		return pstmt.executeQuery();
	}





	public ResultSet queryByName(Connection con, String userName) throws Exception {
		String sql = "select * from reader where reader_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		return pstmt.executeQuery();
	}

	
	public ResultSet queryById(Connection con, int readerId) throws Exception {
		String sql = "select * from reader where reader_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, readerId);
		return pstmt.executeQuery();
	}

	
	public int add(Connection con, Reader reader) throws Exception {
		String sql = "insert into reader(reader_name,reader_password) values(?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, reader.getReader_name());
		pstmt.setString(2, reader.getReader_password());
		return pstmt.executeUpdate();
	}

	


	
	public Reader getReaderById(Connection con, int readerId) throws Exception {
		String sql = "SELECT * FROM reader WHERE reader_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, readerId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			Reader reader = new Reader();
			reader.setReader_id(rs.getInt("reader_id"));
			reader.setReader_name(rs.getString("reader_name"));
			reader.setReader_phone(rs.getString("reader_phone"));
			reader.setReader_password(rs.getString("reader_password"));
			reader.setReader_nickname(rs.getString("reader_nickname"));
			reader.setReader_gender(rs.getString("reader_gender"));
			reader.setReader_email(rs.getString("reader_email"));
			reader.setReader_address(rs.getString("reader_address"));
			// ... and other fields like avatar, birthday etc. if needed
			return reader;
		}
		return null;
	}

	
	public int updatePersonalInfo(Connection con, int readerId, String username, String password, String nickname, String phone,
								  String gender, String avatar, String address, String email,
								  String birthday, String bio) throws Exception {
		String sql = "UPDATE reader SET reader_name=?, reader_password=?, reader_phone=?, reader_nickname=?, reader_gender=?, reader_avatar=?, reader_address=?, reader_email=?, reader_birthday=?, reader_bio=? WHERE reader_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		pstmt.setString(3, phone);
		pstmt.setString(4, nickname);
		pstmt.setString(5, gender);
		pstmt.setString(6, avatar);
		pstmt.setString(7, address);
		pstmt.setString(8, email);
		pstmt.setString(9, birthday);
		pstmt.setString(10, bio);
		pstmt.setInt(11, readerId);
		return pstmt.executeUpdate();
	}

	
	public int updatePassword(Connection con, int readerId, String newPassword) throws Exception {
		String sql = "UPDATE reader SET reader_password=? WHERE reader_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, newPassword);
		pstmt.setInt(2, readerId);
		return pstmt.executeUpdate();
	}
}