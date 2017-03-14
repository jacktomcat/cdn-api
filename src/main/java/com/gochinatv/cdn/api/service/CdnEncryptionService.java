package com.gochinatv.cdn.api.service;

import com.alibaba.fastjson.JSONObject;


public interface CdnEncryptionService{
   
	public void testScope();
	
	
	 /**
     * 获取动态防盗链的码
     * @param token
     * @return
     */
	public JSONObject getCdnKey(String token) throws Exception;
	
	
	/**
	 * 加密播放地址url
	 * @param url
	 * @param st   过期时间单位秒
	 * @return
	 * @throws Exception
	 */
	public JSONObject getPlayUrl(String url,int st) throws Exception;
}
