package model;


public class Reader {
	// 属性: reader_id
	// 类型: int
	// 作用: 存储读者的唯一ID。
	private int reader_id;// 读者ID
	// 属性: reader_name
	// 类型: String
	// 作用: 存储读者的姓名。
	private String reader_name;// 读者名
	// 属性: reader_phone
	// 类型: String
	// 作用: 存储读者的手机号码。
	private String reader_phone;// 读者手机号
	// 属性: reader_password
	// 类型: String
	// 作用: 存储读者的账户密码。
	private String reader_password;// 读者密码
	// 属性: reader_nickname
	// 类型: String
	// 作用: 存储读者的昵称。
	private String reader_nickname;// 读者昵称
	// 属性: reader_gender
	// 类型: String
	// 作用: 存储读者的性别。
	private String reader_gender;// 性别
	// 属性: reader_avatar
	// 类型: String
	// 作用: 存储读者头像图片的路径。
	private String reader_avatar;// 头像路径
	// 属性: reader_address
	// 类型: String
	// 作用: 存储读者的收货地址。
	private String reader_address;// 收货地址
	// 属性: reader_email
	// 类型: String
	// 作用: 存储读者的电子邮件地址。
	private String reader_email;// 读者邮箱
	// 属性: reader_birthday
	// 类型: String
	// 作用: 存储读者的生日信息。
	private String reader_birthday;// 读者生日
	// 属性: reader_bio
	// 类型: String
	// 作用: 存储读者的个人简介。
	private String reader_bio;// 读者简介

	
	public Reader() {
		super();
	}

	
	public Reader(int reader_id, String reader_name, String reader_phone, String reader_password) {
		super();
		this.reader_id = reader_id;
		this.reader_name = reader_name;
		this.reader_phone = reader_phone;
		this.reader_password = reader_password;
	}

	
	public Reader(String reader_name, String reader_password) {
		super();
		this.reader_name = reader_name;
		this.reader_password = reader_password;
	}

	
	public int getReader_id() {
		return reader_id;
	}

	
	public void setReader_id(int reader_id) {
		this.reader_id = reader_id;
	}

	
	public String getReader_name() {
		return reader_name;
	}

	
	public void setReader_name(String reader_name) {
		this.reader_name = reader_name;
	}


	public String getReader_phone() {
		return reader_phone;
	}


	public void setReader_phone(String reader_phone) {
		this.reader_phone = reader_phone;
	}

	
	public String getReader_password() {
		return reader_password;
	}

	
	public void setReader_password(String reader_password) {
		this.reader_password = reader_password;
	}

	
	public String getReader_nickname() {
		return reader_nickname;
	}

	
	public void setReader_nickname(String reader_nickname) {
		this.reader_nickname = reader_nickname;
	}


	public String getReader_gender() {
		return reader_gender;
	}

	
	public void setReader_gender(String reader_gender) {
		this.reader_gender = reader_gender;
	}

	public String getReader_avatar() {
		return reader_avatar;
	}

	public void setReader_avatar(String reader_avatar) {
		this.reader_avatar = reader_avatar;
	}

	
	public String getReader_address() {
		return reader_address;
	}


	public void setReader_address(String reader_address) {
		this.reader_address = reader_address;
	}

	public String getReader_email() {
		return reader_email;
	}

	
	public void setReader_email(String reader_email) {
		this.reader_email = reader_email;
	}

	
	public String getReader_birthday() {
		return reader_birthday;
	}

	
	public void setReader_birthday(String reader_birthday) {
		this.reader_birthday = reader_birthday;
	}


	public String getReader_bio() {
		return reader_bio;
	}

	public void setReader_bio(String reader_bio) {
		this.reader_bio = reader_bio;
	}
}