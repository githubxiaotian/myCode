package org.yugh.test.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yugh.test.mvc.utils.StringTools;

import net.sf.json.JSONObject;

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
* @name：      HttpServer.java
* @creation：  2017年7月21日 下午5:13:17
* @description：
* @version： 1.0
*/
@RequestMapping("/httpClientTest")
@Controller
public class HttpServer {

	
	@RequestMapping("/newHttpClient.do")
	@ResponseBody
	public String testNewHttpClient(String path, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonHead = request.getParameter("jsonHead") == null ? "" : request.getParameter("jsonHead");
		if(StringTools.isEmpty(jsonHead)){
			map.put("msg", "no parameter !! ");
			return JSONObject.fromObject(map).toString();
		}
		if(StringTools.isNotEmpty(jsonHead)){
			JSONObject jsonObject = JSONObject.fromObject(jsonHead);
			map.put("msg", jsonObject);
			return JSONObject.fromObject(map).toString();
		}
		map.put("msg", "success !!");
		return JSONObject.fromObject(map).toString();
	}
	
}
