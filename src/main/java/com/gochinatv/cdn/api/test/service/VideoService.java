package com.gochinatv.cdn.api.test.service;

import java.util.List;


public interface VideoService {

	List<String> getVideoList();
	
    int getCache();
	
    String createMessage();
}
