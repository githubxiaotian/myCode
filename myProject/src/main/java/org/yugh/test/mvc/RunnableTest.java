package org.yugh.test.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.yugh.test.mvc.utils.StringTools;

public class RunnableTest {

	Runnable runnable = new Runnable() {
		
		@SuppressWarnings({ "static-access", "deprecation" })
		@Override
		public synchronized void run() {
			long time = System.currentTimeMillis();
			MianClient main = new MianClient();
			String flag = main.isStartThreads();
			if(StringTools.isNotEmpty(flag)){
				String jsonData = "{\"name\":\"yugh\",\"address\":\"beijing\"}";
				try {
					TestHttpPost(flag, jsonData);
					thread.sleep(100);
					thread2.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long time1 = System.currentTimeMillis();
				System.out.println( thread.getName() +"线程耗费时间:"+ String.valueOf(time1-time) + "毫秒！");
				System.out.println( thread2.getName() +"线程耗费时间:"+ String.valueOf(time1-time) + "毫秒！");
				thread.run();
				thread2.run();
			}
		}
	};
	Thread thread = new Thread(runnable);
	Thread thread2 = new Thread(runnable);
	
	public static void main(String[] args) {
		RunnableTest test = new RunnableTest();
		test.thread.setName("yugh1111111");
		test.thread2.setName("yugh2222222");
		test.thread.start();
		test.thread2.start();
	}
	
	
	
	
	//httpClient版本等级提高，取消defaultHttpClient
		public static void TestHttpPost(String url, String jsonData){
			//声明新的httpclient
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);		
			//拼接参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("jsonHead", jsonData));
	        System.out.println("==== 提交参数 ======" +list);
	        CloseableHttpResponse response  = null;
	        try {
	        	httpPost.setEntity(new UrlEncodedFormEntity(list));
				response = httpClient.execute(httpPost);
				System.out.println("========HttpResponseProxy：========"+response.getStatusLine());
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String result = EntityUtils.toString(entity, "UTF-8");
					System.out.println("========接口返回=======" +result);
				}
				EntityUtils.consume(entity);
	        } catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(response != null){
					try {
						response.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(httpClient != null){
					try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
}
