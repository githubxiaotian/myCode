package org.yugh.test.mvc;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yugh.test.mvc.common.Running;
import org.yugh.test.mvc.utils.StringTools;

/**
*
* ━━━━━━神兽出没━━━━━━
* 　　　┏┓　　　┏┓
* 　　┏┛┻━━━┛┻┓
* 　　┃　　　　　　　┃
* 　　┃　　　━　　　┃
* 　　┃　┳┛　┗┳　┃
* 　　┃　　　　　　　┃
* 　　┃　　　┻　　　┃
* 　　┃　　　　　　　┃
* 　　┗━┓　　　┏━┛
* 　　　　┃　　　┃    
* 　　　　┃　　　┃
* 　　　　┃　　　┗━━━┓
* 　　　　┃ author:yugh  ┣┓
* 　　　　┃　　　　　　　┏┛
* 　　　　┗┓┓┏━┳┓┏┛
* 　　　　　┃┫┫　┃┫┫
* 　　　　　┗┻┛　┗┻┛
* ━━━━━━感觉萌萌哒━━━━━━
* 
* @author：  YuGH
* @e_mail：  yugh@chinatelling.com
* @name：      MianClient.java
* @creation：  2017年7月21日 下午5:12:31
* @description：
* @version： 1.0
*/
public class MianClient {

	private static Log logger = LogFactory.getLog(MianClient.class);
	
	@SuppressWarnings("unchecked")
	public static void showSql(String app_name) throws Exception{
		String sql = "select a.task_sql,a.THREAD_NAME,a.class from conf_thread a where a.app_name=?";
		List<Map<String, Object>> sList = Running.JDBC_TEMPLATE.queryForList(sql, app_name);
		for (Map<String, Object> map : sList) {
			String threadName = map.get("THREAD_NAME")+"";
			String resultSQL = map.get("task_sql")+"";
			String classs = map.get("class")+"";
			Class<Thread> clazz = (Class<Thread>) Class.forName(classs);
			List<Map<String, Object>> aList = Running.JDBC_TEMPLATE.queryForList(resultSQL);
			for (Map<String, Object> map2 : aList) {
				String httpParams = map2.get("value")+"";
				//开始发送http
				if(StringTools.isNotEmpty(httpParams)){
					String jsonData = "{\"name\":\"yugh\",\"address\":\"beijing\"}";
					Thread thread = clazz.getConstructor(String.class, String.class).newInstance(jsonData, httpParams);
					thread.setName(threadName);
					thread.setName(threadName +"2");
					System.out.println(" ======线程名字 ======== " +thread.getName());
					logger.debug(" ======线程名字 ======== " +thread.getName());
					thread.start();
					//TestHttpPost(httpParams, params);
				}
			}
		}
		return ;
	}
	
	
	
	public static void main(String[] args) {
		String app_name = System.getProperty("app_name");
		if(StringTools.isEmpty(app_name)){
			System.out.println("please define app_name by vm args: -Dapp_name=XXX");
			return ;
		}
		try {
			showSql(app_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//runnable使用
	public static String getPropertiesName(){
		String app_name = System.getProperty("app_name");
		if(StringTools.isEmpty(app_name)){
			System.out.println("please define app_name by vm args: -Dapp_name=XXX");
			return null;
		}else{
			return app_name;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String isStartThreads(){
		String app_name = getPropertiesName();
		if(StringTools.isNotEmpty(app_name)){
			String sql = "select a.task_sql,a.THREAD_NAME,a.class from conf_thread a where a.app_name=?";
			List<Map<String, Object>> sList = Running.JDBC_TEMPLATE.queryForList(sql, app_name);
			try {
				if(sList != null && sList.size() > 0){
					for (Map<String, Object> map : sList) {
						String threadName = map.get("THREAD_NAME")+"";
						String resultSQL = map.get("task_sql")+"";
						String classs = map.get("class")+"";
						Class<Thread> clazz = (Class<Thread>) Class.forName(classs);
						List<Map<String, Object>> ssList = Running.JDBC_TEMPLATE.queryForList(resultSQL);
						for (Map<String, Object> map2 : ssList) {
							String httpUrl = map2.get("value")+"";
							if(StringTools.isNotEmpty(httpUrl)){
								return httpUrl;
							}
						}
					}
				}
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
}
