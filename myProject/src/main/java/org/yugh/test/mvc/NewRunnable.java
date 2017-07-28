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

public class NewRunnable implements Runnable {

	private static int count;

	public NewRunnable() {
		count = 0;
	}

	@Override
	public void run() {
		method1();
		method2();

	}

	
	public static void main(String[] args) {
		NewRunnable newRunnable1 = new NewRunnable();
		NewRunnable newRunnable2 = new NewRunnable();
		Thread thread1 = new Thread(newRunnable1, "SyncThread1");
		Thread thread2 = new Thread(newRunnable2, "SyncThread2");
		thread1.start();
		thread2.start();
	}
	
	
	
	// synchronized同步精静态方法
	@SuppressWarnings("static-access")
	public synchronized static void method1() {
		long time = System.currentTimeMillis();
		MianClient main = new MianClient();
		String flag = main.isStartThreads();
		if (StringTools.isNotEmpty(flag)) {
			String jsonData = "{\"name\":\"yugh\",\"address\":\"beijing\"}";
			TestHttpPost(flag, jsonData);
			long time1 = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName() + "线程耗费时间:" + String.valueOf(time1 - time) + "毫秒！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				new Thread().stop();
			}
		}
	}
	
	public synchronized static void method2() {
	      for (int i = 0; i < 5; i ++) {
	         try {
	            System.out.println(Thread.currentThread().getName() + ":" + (count++));
	            Thread.sleep(100);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }
	      }
	   }

	
	
	//
	// httpClient版本等级提高，取消defaultHttpClient
	public static void TestHttpPost(String url, String jsonData) {
		// 声明新的httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 拼接参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("jsonHead", jsonData));
		//System.out.println("==== 提交参数 ======" + list);
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			response = httpClient.execute(httpPost);
			//System.out.println("========HttpResponseProxy：========" + response.getStatusLine());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("========接口返回=======" + result);
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
