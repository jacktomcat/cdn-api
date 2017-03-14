package com.gochinatv.cdn.api.service.impl;

import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.gochinatv.cdn.api.commons.ConstantUtils;
import com.gochinatv.cdn.api.commons.HttpClientTools;
import com.gochinatv.cdn.api.service.CdnEncryptionService;
import com.gochinatv.cdn.api.service.RedisService;


@Service
//@Scope("singleton")
@Scope("prototype")
public class CdnEncryptionServiceImpl implements CdnEncryptionService{
    
	private Logger logger = LoggerFactory.getLogger(CdnEncryptionServiceImpl.class);
	
	@Autowired
	private RedisService redisService;
	
	@Value("${CDN_KEY}")
	private String CDN_KEY;
	
	private int number = 0;
	
	public void testScope(){
		number++;
		System.out.println("============service number=======:"+number);
	}
	
	 /**
     * 获取动态防盗链的码
     * @param token
     * @return
     */
	public JSONObject getCdnKey(String token) throws Exception {
		JSONObject rstObj = null;
		String value = redisService.get(ConstantUtils.SECURITY_CDN_KEY);
		if (StringUtils.isEmpty(value)) {
			try{
				value = HttpClientTools.Get(CDN_KEY);
				redisService.setExpire(ConstantUtils.SECURITY_CDN_KEY, value, ConstantUtils.TIME_3600);
			}catch(Exception e){
				value = "{\"version\": \"1.0\",\"key\": \"vego.tv\"}";//给出一个默认的
				logger.error("************cdn-api 获取 {} 失败************",CDN_KEY);
			}
		}
		rstObj = JSONObject.parseObject(value);
		return rstObj;
	}
	
	
	/**
	 * 加密播放地址url
	 * @param url
	 * @param st  过期时间单位秒
	 * @return
	 * @throws Exception
	 */
	public JSONObject getPlayUrl(String url,int st) throws Exception {
		JSONObject rstObj = new JSONObject();
		JSONObject keyJson = getCdnKey("");
		
		URI uri = new URI(url);
		String path = uri.getPath();
		String query = uri.getQuery() == null ? "" : uri.getQuery();
		String playUrl = getProxyEncryptPlayUrl(path, query, keyJson.getString("key"),st);
		String host = url.replaceAll(path + query, "");
		rstObj.put("url", host + playUrl);
		return rstObj;
	}
	
	
	/**
	 * CDN 加密直播url
	 * @param playUrl
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	private String getProxyEncryptPlayUrl(String path, String query, String pass, int st) throws Exception {
		StringBuilder url = new StringBuilder(path);
		url.append("?");
		url.append(query.equals("") ? "st=" : (query + "&st="));
		url.append(st + System.currentTimeMillis() / 1000);

		String passPre = url.toString() + "&pass=" + pass;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(passPre.getBytes(), 0, passPre.length());
		String token = new BigInteger(1, m.digest()).toString(16);

		url.append("&token=");
		url.append(token);
		return url.toString();
	}
	
}
