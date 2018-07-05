package com.gochinatv.cdn.api.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gochinatv.cdn.api.commons.HttpClientTools;
import com.gochinatv.cdn.api.entity.Validation;
import com.gochinatv.cdn.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.entity.User;
import com.gochinatv.cdn.api.service.CdnEncryptionService;
import com.gochinatv.cdn.api.service.impl.LocalCacheImpl;


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
	
	@Autowired
	private LocalCacheImpl localCacheImpl;

	@Autowired
	private RedisService redisServiceImpl;

	
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

		try {
			//String result = HttpClientTools.Get("https://www.douban.com/");
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//String result = HttpClientTools.Get("https://www.baidu.com");
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";
	}

	/**
	 * 必须添加 validation-api.jar和具体的实现(这里使用的是hibernte-validate的jar)
	 * @param validation
	 * @param result
     * @return
     */
	@RequestMapping("/validate")
	public String validate(@Validated Validation validation, BindingResult result) {
		if(result.hasErrors()){
			List<ObjectError> allErrors = result.getAllErrors();
			System.out.println(allErrors.size()+"");
			for (ObjectError error : allErrors) {
				System.out.println(error.getCode()+" "+error.getDefaultMessage());
			}

			for (FieldError fieldError:result.getFieldErrors()) {
				System.out.println(fieldError.getField()+"   "+ fieldError.getRejectedValue());
			}

		}
		return "index";
	}

	@RequestMapping("/value_support")
	public String value_support() {
		System.out.println("==========CDN_KEY:==="+CDN_KEY.get(0)+"========"+CDN_KEY.get(1));
		System.out.println(userMap.get("name1")+"====="+userMap.get("name2"));
		return "index";
	}
	
	@RequestMapping("/local_value/{id}")
	public /*synchronized*/ String getValue(@PathVariable int id) {
		System.out.println(localCacheImpl.getValue(id));
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


	/**
	 * nginx 获取post 参数
	 * @param data1
	 * @param data2
     * @return
     */
	@RequestMapping(value = "/postdata",method = RequestMethod.POST)
	public String postdata(String data1,String data2,String data3) {
		System.out.println("data1:"+data1);
		System.out.println("data2:"+data1);
		System.out.println("data3:"+data1);
		System.out.println("data4:"+data1);
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
