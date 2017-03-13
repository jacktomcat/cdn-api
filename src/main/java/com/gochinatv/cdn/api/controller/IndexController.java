package com.gochinatv.cdn.api.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.entity.User;
import com.gochinatv.cdn.api.service.CdnEncryptionService;


@Controller
@RequestMapping("")
//@Scope("singleton")
//@Scope("prototype")
//@Scope("request")
//@Scope("session")
public class IndexController extends BaseHandler{
   
	private int number = 0;
	
	//测试service中的scope的作用域
	@Autowired
	private CdnEncryptionService cdnEncryptionService1;
	
	//测试service中的scope的作用域
	@Autowired
	private CdnEncryptionService cdnEncryptionService2;
	
	/**
	 * <mvc:redirect-view-controller redirect-url="/index" path="/"/>
	 * 表示项目直接访问根目录直接跳转至  /index.jsp
	 * @return
	 */
	
	@RequestMapping("/index")
	public /*synchronized*/ String index() {
		number++;
		System.out.println("=============number===========:"+number);
		cdnEncryptionService1.testScope();
		cdnEncryptionService2.testScope();
		return "index";
	}
	
	
	@RequestMapping("/output_format")
    @ResponseBody
	public User json_output() {
		User user = null;
		try {
			user = new User(1, "tomcat", 29, "834812348134");
		}catch(Exception e){
		    e.printStackTrace();
		}
		return user;
	}
	
	
	
	/**
	 * 消息国际化，但是此方法是多余的方法，只需要在jsp中通过js修改客户端cookie的名称即可
	 * 切换国际化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/change")
	public String change(HttpServletRequest request,HttpServletResponse response) {
		String language = request.getParameter("language");
		//Cookie cookie = new Cookie("language", language);
		//response.addCookie(cookie);
		return "index";
	}
	
	
}
