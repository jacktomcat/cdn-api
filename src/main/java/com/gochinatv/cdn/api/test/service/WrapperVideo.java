package com.gochinatv.cdn.api.test.service;

import java.util.ArrayList;
import java.util.List;


public abstract class WrapperVideo implements VideoService{

	private int cache;
	
	public List<String> getVideoList(){
		List<String> videoList = new ArrayList<>();
		videoList.add("我是抽象 － 人民的名义");
		return videoList;
	}
	
	public String createMessage(){
		String targetName = this.setTargetName();
		return targetName + ": 抽象类中的部分实现";
	}
	
	public abstract String setTargetName();

	public void setCache(int cache) {
		this.cache = cache;
	}

	public int getCache() {
		return cache;
	}
}
