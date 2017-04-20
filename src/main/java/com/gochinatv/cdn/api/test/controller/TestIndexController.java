package com.gochinatv.cdn.api.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.test.service.VideoService;

@Controller
@RequestMapping("/test/index")
public class TestIndexController extends BaseHandler{
   
	private VideoService tvVideoServiceImpl;
	
	private VideoService letvVideoServiceImpl;
	
	public void setTvVideoServiceImpl(VideoService tvVideoServiceImpl) {
		this.tvVideoServiceImpl = tvVideoServiceImpl;
	}

	public void setLetvVideoServiceImpl(VideoService letvVideoServiceImpl) {
		this.letvVideoServiceImpl = letvVideoServiceImpl;
	}


	/**
	 * @return
	 */
	@RequestMapping("/list")
	public String list() {
		int tvCache = tvVideoServiceImpl.getCache();
		int letvCache =letvVideoServiceImpl.getCache();
		System.out.println("tvCache:"+tvCache);
		System.out.println("letvCache:"+letvCache);
		return "test";
	}
	
}
