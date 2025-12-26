package model;


public class Book {

	
	private int book_id;// 图书编号
	
	private String book_name;// 图书名
	
	private String book_writer;// 作者
	
	private String book_publish;// 出版社
	
	private int book_amount;// 图书库存数量
	
	private double book_price;// 图书价格
	
	private double book_discount = 1.0;// 图书折扣
	
	private String book_cover; // 图书封面图片路径
	
	private String book_description; // 图书简介
	
	private String book_isbn; // ISBN编号
	
	private String book_category; // 图书分类

	
	public Book() {
		super();
	}

	
	public Book(int book_id) {
		super();
		this.book_id = book_id;
	}





	public Book(int book_id, String book_name, String book_writer, String book_publish, int book_amount,
			double book_price) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.book_writer = book_writer;
		this.book_publish = book_publish;
		this.book_amount = book_amount;
		this.book_price = book_price;
	}




	
	public Book(int book_id, String book_name, String book_writer, String book_publish, double book_price, double book_discount, int book_amount, String book_cover, String book_description) {
		this.book_id = book_id;
		this.book_name = book_name;
		this.book_writer = book_writer;
		this.book_publish = book_publish;
		this.book_price = book_price;
		this.book_discount = book_discount;
		this.book_amount = book_amount;
		this.book_cover = book_cover;
		this.book_description = book_description;
	}

	
	public int getBook_id() {
		return book_id;
	}

	
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	
	public String getBook_name() {
		return book_name;
	}

	
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	
	public String getBook_writer() {
		return book_writer;
	}

	
	public void setBook_writer(String book_writer) {
		this.book_writer = book_writer;
	}

	
	public String getBook_publish() {
		return book_publish;
	}

	
	public void setBook_publish(String book_publish) {
		this.book_publish = book_publish;
	}



	
	public int getBook_amount() {
		return book_amount;
	}


	public void setBook_amount(int book_amount) {
		this.book_amount = book_amount;
	}


	public double getBook_price() {
		return book_price;
	}

	
	public void setBook_price(double book_price) {
		this.book_price = book_price;
	}

	
	public double getBook_discount() {
		return book_discount;
	}

	
	public void setBook_discount(double book_discount) {
		this.book_discount = book_discount;
	}

	
	public String getBook_cover() {
		return book_cover;
	}

	public void setBook_cover(String book_cover) {
		this.book_cover = book_cover;
	}


	public String getBook_description() {
		return book_description;
	}

	
	public void setBook_description(String book_description) {
		this.book_description = book_description;
	}

	public String getBook_isbn() {
		return book_isbn;
	}

	
	public void setBook_isbn(String book_isbn) {
		this.book_isbn = book_isbn;
	}

	
	public String getBook_category() {
		return book_category;
	}

	
	public void setBook_category(String book_category) {
		this.book_category = book_category;
	}

}
