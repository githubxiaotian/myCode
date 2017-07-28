package org.yugh.test;

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
* @name：      HttpClientUtils.java
* @creation：  2017年7月24日 下午3:26:38
* @description：httpClient新版本调用工具
* @version： 1.0
*/
public class HttpClientUtils {

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
	
	
	public static void main(String[] args) {
		String url = "http://localhost:8070/myProject/httpClientTest/newHttpClient.do";
		String params = "{\"name\":\"yugh\",\"address\":\"beijing\"}";
		TestHttpPost(url, params);
	}
	
	
	

}
