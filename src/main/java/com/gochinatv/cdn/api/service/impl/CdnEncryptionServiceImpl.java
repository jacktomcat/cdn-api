package com.gochinatv.cdn.api.service.impl;

import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.gochinatv.cdn.api.commons.ConstantUtils;
import com.gochinatv.cdn.api.commons.HttpClientTools;
import com.gochinatv.cdn.api.service.CdnEncryptionService;
import com.gochinatv.cdn.api.service.RedisService;


@Service
public class CdnEncryptionServiceImpl implements CdnEncryptionService{
    
	private Logger logger = LoggerFactory.getLogger(CdnEncryptionServiceImpl.class);
	
	@Autowired
	private RedisService redisService;
	
	@Value("${CDN_KEY}")
	private String CDN_KEY;
	
	/*@Value("${H5.LIVE_CND_HOST}")
	private String LIVE_CND_HOST;*/
	
	 /**
     * 获取动态防盗链的码
     * @param token
     * @return
     */
	public JSONObject getCdnKey(String token) throws Exception {
		JSONObject rstObj = null;
		String value = redisService.get(ConstantUtils.SECURITY_CDN_KEY);
		if (StringUtils.isEmpty(value)) {
			/*String[] urls = CDN_KEY.split(",");
			for (String url : urls) {
				try{
					value = HttpClientTools.Get(url);
				}catch(Exception e){
					logger.error("************cdn-api 获取 {} 失败************",url);
					continue;
				}
			}*/
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
		/*String key = String.format(ConstantUtils.SECURITY_PLAY_URL, HttpParamsUtils.replaceForceUpdate(HttpParamsUtils.getRequest()));
		if(!StringUtils.isEmpty(rstObj.getString("url"))){
			String value = rstObj.toString();
			redisService.setExpire(key, value, ConstantUtils.TIME_18000);
		}*/
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
		//String key = String.format(SECURITY_PLAY_URL, "type:EncryptPlayUrl,playUrl:" + (path + query) + ",pass:" + pass);
		//String value = redisService.get(key);
		//if (StringUtils.isEmpty(value)) {
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
			//redisService.setExpire(key, value, ConstantUtils.TIME_18000);//5小时的缓存时间
		//}
		return url.toString();
	}
	
}
