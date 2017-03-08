package com.gochinatv.cdn.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gochinatv.cdn.api.controller.base.BaseHandler;


@Controller
@RequestMapping("/test")
public class IndexController extends BaseHandler{

	
	/**
	 * <mvc:redirect-view-controller redirect-url="/test/index" path="/"/>
	 * 表示项目直接访问根目录直接跳转至  /test/index
	 * @return
	 */
	@RequestMapping("/index")
    @ResponseBody
	public JSONObject index() {
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
	
	
}
