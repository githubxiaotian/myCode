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
* @name：      WorkThreadTest.java
* @creation：  2017年7月21日 下午5:13:03
* @description：
* @version： 1.0
*/
public class WorkThreadTest extends Thread {

	String jsonData;
	String url;
	
	public WorkThreadTest() {
		// TODO Auto-generated constructor stub
	}
	
	public WorkThreadTest(String jsonData, String url) {
		super();
		this.jsonData = jsonData;
		this.url = url;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				long time = System.currentTimeMillis();
				int sum = 0;
				if(!true){
					for (int i = 1; i < 1001; i++) {
						TestHttpPost(url,jsonData);
						sleep(1000*3);
						sum++;
						System.out.println(sum);
						if(sum == 1000){
							long time1 = System.currentTimeMillis();
							System.out.println("1000次线程耗费时间:"+ String.valueOf(time1-time) + "毫秒！");
							wait();
						}
					}
				}
				
				TestHttpPost(url, jsonData);
				sleep(1000 * 3);
				long time1 = System.currentTimeMillis();
				System.out.println("线程耗费时间:"+ String.valueOf(time1-time) + "毫秒！");
				notifyAll();
			} catch (Exception e) {
				try {
					sleep( 1000 * 6);
				} catch (Exception e2) {
					e.printStackTrace();
				}
			}
			
		}
	
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
