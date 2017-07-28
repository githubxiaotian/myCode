package org.yugh.test;

public class TestUnicode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		validateUnicode("helloWorld");
		validateChinese("\uFFFD\uFFFD\uFFFD\u0234\uFFFD\u02B1\uFFFD\u48FA\uFFFD\uFFFD\u03BBms");
	}

	
	
	/**
	 * 中文转unicode
	 * @param str
	 */
	public static void validateUnicode(String str){
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			Character cr = str.charAt(i);
			bf.append("\\u" + Integer.toHexString(cr));
		}
		System.out.println(bf.toString());
	}
	
	
	/**
	 * unicode转中文
	 * @param unicode
	 */
	public static void validateChinese(String unicode){
		try {
			String result = new String(unicode.getBytes("utf-8"), "utf-8");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
