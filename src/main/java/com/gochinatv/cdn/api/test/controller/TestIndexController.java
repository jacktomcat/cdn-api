package com.gochinatv.cdn.api.test.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.test.service.VideoService;


@Controller
@RequestMapping("/test/index")
public class TestIndexController extends BaseHandler{
   
	@Autowired
	private VideoService tvVideoServiceImpl;
	
	@Autowired
	private VideoService letvVideoServiceImpl;
	
	@Autowired
	private VideoService h5VideoServiceImpl;
	
	@Autowired
	private VideoService mobileVideoServiceImpl;

	/**Is it an abstract class? 不能在配置文件中或者注解的方式注入实体，必须是实现类
	 * private WrapperVideo wrapperVideo;
	 */

	/**
	 * @return
	 */
	@RequestMapping("/list")
	public String list() {
		int tvCache = tvVideoServiceImpl.getCache();
		int letvCache =letvVideoServiceImpl.getCache();
		System.out.println("tvCache抽象类中得到的值为:"+tvCache);
		System.out.println("letvCache抽象类中得到的值为:"+letvCache);
		
		List<String> videoList = tvVideoServiceImpl.getVideoList();
		for (String video : videoList) {
			System.out.println("videoList 得到的值为:"+video);
		}
		
		System.out.println(tvVideoServiceImpl.toString());
		System.out.println(letvVideoServiceImpl.toString());
		
		String createMessage = tvVideoServiceImpl.createMessage();
		System.out.println("tvVideoServiceImpl具体和抽象结合的结果："+createMessage);
		System.out.println("=========================华丽的分割线=========================");
		
		System.out.println("mobileVideoServiceImpl 具体和抽象结合的结果："+mobileVideoServiceImpl.createMessage());
		System.out.println("h5VideoServiceImpl 具体和抽象结合的结果："+h5VideoServiceImpl.createMessage());
		System.out.println("mobileVideoServiceImpl 抽象缓存结果："+mobileVideoServiceImpl.getCache());
		System.out.println("h5VideoServiceImpl 抽象缓存结果："+h5VideoServiceImpl.getCache());
		return "test";
	}
	
	
}
