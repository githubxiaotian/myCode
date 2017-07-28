package org.yugh.test.mvc.utils;

public class StringTools {

	public static boolean isEmpty(String obj){
		if(obj == null || obj.length() == 0 || obj.trim().length() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String obj){
		if(obj != null && obj.length() > 0 && !"".equals(obj)){
			return true;
		}
		return false;
	}
	
}
