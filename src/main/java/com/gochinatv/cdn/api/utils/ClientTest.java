package com.gochinatv.cdn.api.utils;

import java.util.HashMap;
import java.util.Map;

import com.gochinatv.cdn.api.commons.ConstantUtils;
import com.gochinatv.cdn.api.commons.HttpClientTools;

public class ClientTest {
   
	
	public static void main(String[] args) throws Exception {
		Map<String, String> heads = new HashMap<>();
		heads.put("Cookie", "JSESSIONID=1EC3F48E8F2D468C8B74FCFF74B0D828");
		System.out.println(ConstantUtils.sessionId+":::::");
		String message = HttpClientTools.Get("http://localhost:8080/cdn-api/client/session/android",heads);
		System.out.println(message+"=======");
	}
	
}
