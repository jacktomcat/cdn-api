package com.gochinatv.cdn.api.commons;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.util.*;


public class HttpClientTools {

    public static String UTF8 = "utf-8";

    public static final String APPLICATION_JSON = "application/json";
    
    public static final int TIME_OUT = 10000;
    
    
    public static String Get(String url) throws Exception {
		HttpGet get = new HttpGet(url);
		String result = ReponseGet(get);
		return result;
	}
    
    public static String Get(String url, Map<String, String> heads) throws Exception {
		HttpGet get = new HttpGet(url);
		if (heads != null) {
			for (String h : heads.keySet()) {
				get.setHeader(h, heads.get(h));
			}
		}
		String result = ReponseGet(get);
		return result;
	}
    
	
	private static String ReponseGet(HttpGet get) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);// 请求超时
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIME_OUT);// 读取超时
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity, UTF8).trim();
		return result;
	}
	

	public static JSONObject GetJson(String url) throws Exception {
        String result = Get(url);
        if (null != result) {
            return JSONObject.parseObject(result);
        }
        return null;
    }
	
	
	public static JSONObject GetJson(String url,Map<String, String> heads) throws Exception {
        String result = Get(url,heads);
        if (null != result) {
            return JSONObject.parseObject(result);
        }
        return null;
    }
	
	
	public static JSONArray GetArray(String url) throws Exception {
        String result = Get(url);
        if (null != result) {
            return JSONObject.parseArray(result);
        }
        return null;
    }
	
	
	public static JSONArray GetArray(String url,Map<String, String> heads) throws Exception {
        String result = Get(url,heads);
        if (null != result) {
            return JSONObject.parseArray(result);
        }
        return null;
    }
	
	
	public static String Post(String url, Map<String, String> heads ,String json) {
		try {
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);

			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost post = new HttpPost(url);

			if (heads != null) {
				for (String h : heads.keySet()) {
					post.setHeader(h, heads.get(h));
				}
			}

			StringEntity se = new StringEntity(json, UTF8);
			post.setEntity(se);

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, UTF8).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
	
	public static String Post(String url, Map<String, String> heads, Map<String, String> params) throws Exception {
		try {
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);

			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost post = new HttpPost(url);

			if (heads != null) {
				for (String h : heads.keySet()) {
					post.setHeader(h, heads.get(h));
				}
			}

			if (params != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, UTF8));
			}

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, UTF8).trim();
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	
	public static JSONObject PostJson(String url,Map<String, String> heads, Map<String, String> params) throws Exception {
        String result = Post(url,heads,params);
        if (null != result) {
            return JSONObject.parseObject(result);
        }
        return null;
    }
	
	
	public static JSONArray PostArray(String url,Map<String, String> heads, Map<String, String> params) throws Exception {
        String result = Post(url,heads,params);
        if (null != result) {
            return JSONObject.parseArray(result);
        }
        return null;
    }
	

}
