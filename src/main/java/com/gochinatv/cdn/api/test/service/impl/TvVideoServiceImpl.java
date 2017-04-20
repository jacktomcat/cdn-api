package com.gochinatv.cdn.api.test.service.impl;

import com.gochinatv.cdn.api.test.service.VideoService;


public class TvVideoServiceImpl implements VideoService{
  
	private int cache;

	public void setCache(int cache) {
		this.cache = cache;
	}

	public int getCache() {
		return cache;
	}
	
	
}
