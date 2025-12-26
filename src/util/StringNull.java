package util;

public class StringNull {
	
	public static boolean isEmpty(String str) {
		// 检查字符串是否为null，或者去除首尾空白后是否为空字符串
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isNotEmpty(String str) {
		// 检查字符串是否不为null，并且去除首尾空白后是否不为空字符串
		if (str != null && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
}
