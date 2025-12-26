package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import util.Connect;


public class OrderDao {
	private Connect conutil = new Connect();
	private BookDao bookDao = new BookDao();


	public List<Order> getUnpaidOrders(int readerId) {
		List<Order> orders = new ArrayList<>();
		Connection con = null;
		try {
			con = conutil.loding();
			String sql = "SELECT o.*, b.book_name, b.book_price, b.book_discount " +
						"FROM book_order o " +
						"JOIN book b ON o.order_book_id = b.book_id " +
						"WHERE o.order_reader_id = ? AND o.order_status = 0";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, readerId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("order_book_id"));
				order.setReaderId(rs.getInt("order_reader_id"));
				order.setBookName(rs.getString("book_name"));
				order.setPrice(rs.getDouble("book_price") * rs.getDouble("book_discount"));
				order.setQuantity(rs.getInt("order_amount"));
				order.setRemark(rs.getString("order_remark"));
				order.setStatus(rs.getInt("order_status"));
				order.setTotal(order.getPrice() * order.getQuantity());
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) conutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orders;
	}


	public Order getOrderById(Connection con, int orderId) throws SQLException {
		String sql = "SELECT o.*, b.book_name, b.book_price, b.book_discount " +
					"FROM book_order o " +
					"JOIN book b ON o.order_book_id = b.book_id " +
					"WHERE o.order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, orderId);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			Order order = new Order();
			order.setOrderId(rs.getInt("order_id"));
			order.setBookId(rs.getInt("order_book_id"));
			order.setReaderId(rs.getInt("order_reader_id"));
			order.setBookName(rs.getString("book_name"));
			order.setPrice(rs.getDouble("book_price") * rs.getDouble("book_discount"));
			order.setQuantity(rs.getInt("order_amount"));
			order.setRemark(rs.getString("order_remark"));
			order.setStatus(rs.getInt("order_status"));
			order.setTotal(order.getPrice() * order.getQuantity());
			return order;
		}
		return null;
	}


	public int deleteOrder(Connection con, int orderId) throws SQLException {
		String sql = "DELETE FROM book_order WHERE order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, orderId);
		return pstmt.executeUpdate();
	}

	public boolean deleteOrder(int orderId) {
		Connection con = null;
		try {
			con = conutil.loding();
			return deleteOrder(con, orderId) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (con != null) conutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public int add(Connection con, Order order) throws SQLException {
		String sql = "INSERT INTO book_order (order_book_id, order_reader_id, order_price, order_amount, order_total, order_remark, order_status, order_detail, order_shipped) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, order.getBookId());
		pstmt.setInt(2, order.getReaderId());
		pstmt.setDouble(3, order.getPrice());
		pstmt.setInt(4, order.getQuantity());
		pstmt.setDouble(5, order.getTotal());
		pstmt.setString(6, order.getRemark());
		pstmt.setInt(7, order.getStatus());
		pstmt.setString(8, order.getOrderDetail());
		pstmt.setInt(9, order.getOrderShipped());
		return pstmt.executeUpdate();
	}


	public int updateStatus(Connection con, int orderId, int status) throws SQLException {
		String sql = "UPDATE book_order SET order_status = ? WHERE order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, status);
		pstmt.setInt(2, orderId);
		return pstmt.executeUpdate();
	}

	
	public ResultSet queryall(Connection con) throws SQLException {
		String sql = "SELECT o.*, b.book_name, r.reader_name " +
				 "FROM book_order o " +
				 "JOIN book b ON o.order_book_id = b.book_id " +
				 "JOIN reader r ON o.order_reader_id = r.reader_id " +
				 "ORDER BY o.order_time DESC";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	
	public ResultSet query(Connection con, int orderid) throws Exception {
		String sql = "SELECT o.*, b.book_name, r.reader_name FROM book_order o " +
				 "JOIN book b ON o.order_book_id = b.book_id " +
				 "JOIN reader r ON o.order_reader_id = r.reader_id " +
				 "WHERE o.order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, orderid);
		return pstmt.executeQuery();
	}

	public ResultSet query2(Connection con, int readerid) throws Exception {
		String sql = "SELECT * FROM `book_order` WHERE order_reader_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, readerid);
		return pstmt.executeQuery();
	}

	


	
	public List<Order> getPaidOrdersByReaderId(int readerId) {
		List<Order> paidOrders = new ArrayList<>();
		Connection con = null;
		try {
			con = new Connect().loding();
			String sql = "SELECT bo.*, b.book_name FROM book_order bo JOIN book b ON bo.order_book_id = b.book_id WHERE bo.order_reader_id = ? AND bo.order_status = 1 ORDER BY bo.pay_time DESC";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, readerId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("order_book_id"));
				order.setReaderId(rs.getInt("order_reader_id"));
				order.setQuantity(rs.getInt("order_amount"));
				order.setPrice(rs.getDouble("order_price"));
				order.setTotal(rs.getDouble("order_total"));
				order.setStatus(rs.getInt("order_status"));
				order.setPayTime(rs.getTimestamp("pay_time"));
				order.setRemark(rs.getString("order_remark"));
				order.setBookName(rs.getString("book_name")); // Set book name from join
				order.setOrderDetail(rs.getString("order_detail")); // 详细信息
				order.setOrderShipped(rs.getInt("order_shipped")); // 发货状态
				paidOrders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return paidOrders;
	}

	


	
	public int updateOrderQuantity(Connection con, int orderId, int newQuantity, double newTotal) throws SQLException {
		String sql = "UPDATE book_order SET order_amount = ?, order_total = ? WHERE order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, newQuantity);
		pstmt.setDouble(2, newTotal);
		pstmt.setInt(3, orderId);
		return pstmt.executeUpdate();
	}

	
	public Order getUnpaidOrderByBookId(Connection con, int readerId, int bookId) throws SQLException {
		String sql = "SELECT * FROM book_order WHERE order_reader_id = ? AND order_book_id = ? AND order_status = 0";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, readerId);
			pstmt.setInt(2, bookId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setBookId(rs.getInt("order_book_id"));
					order.setReaderId(rs.getInt("order_reader_id"));
					order.setPrice(rs.getDouble("order_price"));
					order.setQuantity(rs.getInt("order_amount"));
					order.setTotal(rs.getDouble("order_total"));
					order.setRemark(rs.getString("order_remark"));
					order.setStatus(rs.getInt("order_status"));
					return order;
				}
			}
		}
		return null;
	}


	public void clearCart(int readerId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = new Connect().loding();
			String sql = "DELETE FROM book_order WHERE order_reader_id = ? AND order_status = 0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, readerId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Ensure pstmt and con are closed
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	
	public int updateShippedStatus(Connection con, int orderId, int shipped) throws SQLException {
		String sql = "UPDATE book_order SET order_shipped = ? WHERE order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shipped);
		pstmt.setInt(2, orderId);
		return pstmt.executeUpdate();
	}
}