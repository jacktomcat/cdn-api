package com.gochinatv.cdn.api.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gochinatv.cdn.api.controller.base.BaseHandler;


@Controller
@RequestMapping("")
public class IndexController extends BaseHandler{

	
	/**
	 * <mvc:redirect-view-controller redirect-url="/test/index" path="/"/>
	 * 表示项目直接访问根目录直接跳转至  /test/index
	 * @return
	 */
	@RequestMapping("/logout")
    @ResponseBody
	public JSONObject logout() {
		JSONObject result = null;
		try {
			result = new JSONObject();
			result.put("code",200);
			result.put("message","操作成功!");
		}catch(Exception e){
		    result = failure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index.jsp";
	}
	
	
}
