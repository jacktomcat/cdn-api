package com.gochinatv.cdn.api.controller;


import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.entity.CdnBean;
import com.gochinatv.cdn.api.service.NotNullMockService;


@Controller
@RequestMapping("")
public class RedirectController extends BaseHandler{
   
	@Autowired
	private NotNullMockService notNullMockService;

	/**
	 * 浏览器有302的转发
	 * Location:/cdn-api/index
	 * @return
	 */
	@RequestMapping("/rd")
	public RedirectView rd() {
		return new RedirectView("/index",true);
	}
	
	/**
	 * 浏览器有302的转发
	 * Location:index
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/rd1")
	public void rd1(HttpServletResponse response) throws IOException {
		response.setHeader("Allow", "true");
		response.sendRedirect("index");
	}
	
	/**
	 * 浏览器有302的转发
	 * Location:index
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/rd2")
	public String rd2() throws IOException {
		CdnBean bean = null;
		save(bean);
		
		return "redirect:index";
	}


	/**
	 * java.lang.IllegalArgumentException: Argument for @NotNull parameter
	 * 'bean' of com/gochinatv/cdn/api/controller/RedirectController.save must not be null
	 * @param bean
	 */
	private static void save(@NotNull CdnBean bean){
		System.out.println(bean);
		//System.out.println(bean.getId());
		//System.out.println(bean.getName());
	}


	public static void main(String[] args) {

		CdnBean bean = null;
		save(bean);

	}
	
}
