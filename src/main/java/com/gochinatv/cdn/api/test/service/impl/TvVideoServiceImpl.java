package com.gochinatv.cdn.api.test.service.impl;



import java.util.ArrayList;
import java.util.List;

import com.gochinatv.cdn.api.test.service.WrapperVideo;


public class TvVideoServiceImpl extends WrapperVideo{

	@Override
	public List<String> getVideoList() {
		List<String> videoList = new ArrayList<>();
		videoList.add("我是TvVideoServiceImpl具体实现 － 人民的名义");
		return videoList;
	}

	@Override
	public String setTargetName() {
		return "TvVideoServiceImpl";
	}
	
}
