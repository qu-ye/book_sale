package model;

import java.util.Date;


public class Order {

	// 属性: orderId
	// 类型: int
	// 作用: 存储订单的唯一编号。
	private int orderId;        // 订单编号
	// 属性: bookId
	// 类型: int
	// 作用: 存储订单中图书的ID。
	private int bookId;         // 图书ID
	// 属性: readerId
	// 类型: int
	// 作用: 存储下订单的读者的ID。
	private int readerId;       // 读者ID
	// 属性: quantity
	// 类型: int
	// 作用: 存储购买图书的数量。
	private int quantity;       // 购买数量
	// 属性: price
	// 类型: double
	// 作用: 存储购买时图书的单价（已计算折扣）。
	private double price;       // 购买时单价（已计算折扣）
	// 属性: total
	// 类型: double
	// 作用: 存储订单的总价。
	private double total;       // 订单总价
	// 属性: remark
	// 类型: String
	// 作用: 存储订单的备注信息。
	private String remark;      // 订单备注
	// 属性: status
	// 类型: int
	// 作用: 存储订单的状态（0:未支付, 1:已支付, 2:已取消）。
	private int status;         // 订单状态（0:未支付, 1:已支付, 2:已取消）
	// 属性: bookName
	// 类型: String
	// 作用: 存储关联的图书名称。
	private String bookName;    // 关联的图书名称
	// 属性: payTime
	// 类型: Date
	// 作用: 存储订单的支付时间。
	private Date payTime;
	// 属性: orderDetail
	// 类型: String
	// 作用: 存储订单的详细信息。
	private String orderDetail; // 订单详细信息
	// 属性: orderShipped
	// 类型: int
	// 作用: 存储订单是否已发货（0未发货，1已发货）。
	private int orderShipped;   // 是否已发货（0未发货，1已发货）

	
	public Order() {
	}

	
	public Order(int orderId, int bookId, int readerId, int quantity, double price, double total, String remark, int status, String bookName) {
		this.orderId = orderId;
		this.bookId = bookId;
		this.readerId = readerId;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
		this.remark = remark;
		this.status = status;
		this.bookName = bookName;
	}

	
	public int getOrderId() {
		return orderId;
	}

	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	
	public int getBookId() {
		return bookId;
	}

	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	
	public int getReaderId() {
		return readerId;
	}

	
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public int getQuantity() {
		return quantity;
	}

	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	public double getPrice() {
		return price;
	}

	
	public void setPrice(double price) {
		this.price = price;
	}

	
	public double getTotal() {
		return total;
	}

	
	public void setTotal(double total) {
		this.total = total;
	}

	
	public String getRemark() {
		return remark;
	}

	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public int getStatus() {
		return status;
	}

	
	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getBookName() {
		return bookName;
	}

	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	
	public Date getPayTime() {
		return payTime;
	}

	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	
	public String getOrderDetail() {
		return orderDetail;
	}

	
	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	
	public int getOrderShipped() {
		return orderShipped;
	}

	
	public void setOrderShipped(int orderShipped) {
		this.orderShipped = orderShipped;
	}
}
