package com.gochinatv.cdn.api.controller;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gochinatv.cdn.api.commons.ConstantUtils;
import com.gochinatv.cdn.api.commons.HttpClientTools;
import com.gochinatv.cdn.api.controller.base.BaseHandler;


@Controller
@RequestMapping("client/session")
public class HttpClientTestSessionController extends BaseHandler{
	
	@RequestMapping("/android")
	@ResponseBody
	public String android(HttpServletRequest request) {
		String returnMessage = "";
		String mark = (String)request.getSession().getAttribute("android");
		if(!StringUtils.isEmpty(mark)){
			returnMessage = mark;
			System.out.println("already session =="+request.getSession().getId());
		}else{
			request.getSession().setAttribute("android", System.currentTimeMillis()+"");
			ConstantUtils.sessionId = request.getSession().getId()+"";
			System.out.println(ConstantUtils.sessionId+"====");
		}
		return returnMessage;
	}
	
	
}
