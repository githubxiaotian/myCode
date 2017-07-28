package org.yugh.test.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author：  YuGH
* @e_mail：  
* @name：      TestController.java
* @creation：  2017年6月23日 上午10:36:46
* @description：测试控制类
* @version： 1.0
*/
@Controller
@RequestMapping("/yugh")
public class TestController {

	@RequestMapping("/testPage")
	public String controllerTest(ModelMap map){
		String runTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		map.addAttribute("date", runTime);
		System.out.println("进入controller里,当前时间："+runTime);
		return "testPage/test";
	}
}
