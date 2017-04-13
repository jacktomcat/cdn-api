package com.gochinatv.cdn.api.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	@Value("#{'${CDN_KEY}'.split(',')}")
	private List<String> CDN_KEY;

	@Value("#{PropertySplitter.map('${address}')}")
	private Map<String, String> userMap;
	
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
	
	@RequestMapping("/value_support")
	public /*synchronized*/ String value_support() {
		System.out.println("==========CDN_KEY:==="+CDN_KEY.get(0)+"========"+CDN_KEY.get(1));
		System.out.println(userMap.get("name1")+"====="+userMap.get("name2"));
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
	
	@RequestMapping("/ajax_result")
	public String ajax_result(HttpServletRequest request,HttpServletResponse response) {
		request.setAttribute("name", "zhangsan");
		return "ajax_result";
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
		
		//*********************后台业务代码的方式获取国际化资源文件*************************
		//Locale currentLocale = new Locale("zh", "CN");//设置默认语言
	    //MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource,currentLocale);
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
		String name = messageSourceAccessor.getMessage("index.user.name");
		System.out.println("*******:"+name);
		
		//*******************************************************
		
		
		//Cookie cookie = new Cookie("language", language);
		//response.addCookie(cookie);
		return "index";
	}
	
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		//TimeZone zone = TimeZone.getDefault();
		//TimeZone zone = TimeZone.getTimeZone("Asia/Chongqing");
		TimeZone zone = TimeZone.getTimeZone("America/Dawson");
		String americaId = zone.getID();
		System.out.println(americaId);
		System.out.println(TimeZone.getDefault().getRawOffset()/(3600*1000));
		
		Calendar cl = Calendar.getInstance(zone);
		System.out.println(sdf.format(cl.getTime()));
	}
	
}
