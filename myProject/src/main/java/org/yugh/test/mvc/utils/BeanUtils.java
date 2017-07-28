package org.yugh.test.mvc.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: YuGH
 * @E_mail: yugh@chinatelling.com
 * @Name: BeanUtils.java
 * @Creation: 2017年6月21日 下午3:31:01
 * @Notes: bean获取类
 */

public class BeanUtils implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static ApplicationContext getContext(){
		return context;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		BeanUtils.context = applicationContext;
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
	public static <T> T getBean(String name, Class<T> clazz){
		return context.getBean(name, clazz);
	}
	public static <T> T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}
	
}
