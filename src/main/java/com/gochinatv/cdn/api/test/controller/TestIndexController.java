package com.gochinatv.cdn.api.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gochinatv.cdn.api.controller.base.BaseHandler;

@Controller
@RequestMapping("/test/index")
public class TestIndexController extends BaseHandler{
   
	/**
	 * @return
	 */
	@RequestMapping("/list")
	public String list() {
		
		return "index";
	}
	
}
