package model;


public class Admin {
	// 属性: admin_id
	// 类型: int
	// 作用: 存储管理员的唯一ID。
	private int admin_id;// 管理员ID
	// 属性: admin_name
	// 类型: String
	// 作用: 存储管理员的姓名。
	private String admin_name;// 管理员名
	// 属性: admin_phone
	// 类型: String
	// 作用: 存储管理员的手机号码。
	private String admin_phone;// 管理员手机号
	// 属性: admin_password
	// 类型: String
	// 作用: 存储管理员的账户密码。
	private String admin_password;// 管理员密码

	
	public Admin() {
		super();
	}

	
	public Admin(String admin_name, String admin_password) {
		super();
		this.admin_name = admin_name;
		this.admin_password = admin_password;
	}

	
	public Admin(int admin_id, String admin_name, String admin_phone, String admin_password) {
		super();
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_phone = admin_phone;
		this.admin_password = admin_password;
	}

	
	public int getAdmin_id() {
		return admin_id;
	}

	
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}


	public String getAdmin_name() {
		return admin_name;
	}

	
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	
	public String getAdmin_phone() {
		return admin_phone;
	}

	
	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}

	
	public String getAdmin_password() {
		return admin_password;
	}

	
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

}