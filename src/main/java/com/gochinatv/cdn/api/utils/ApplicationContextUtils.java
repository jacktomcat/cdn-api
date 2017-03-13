package com.gochinatv.cdn.api.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gochinatv.cdn.api.controller.IndexController;

public class ApplicationContextUtils {
    
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-servlet.xml");  
		IndexController index = (IndexController)ctx.getBean("indexController");
		index.index();
		
		IndexController index1 = (IndexController)ctx.getBean("indexController");
		index1.index();
		
		IndexController index2 = (IndexController)ctx.getBean("indexController");
		index2.index();
		
		IndexController index3 = (IndexController)ctx.getBean("indexController");
		index3.index();
	}
}
