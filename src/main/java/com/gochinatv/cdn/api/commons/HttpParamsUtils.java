package com.gochinatv.cdn.api.commons;


import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("all")
public class HttpParamsUtils {
    
	public static String appendParms(Map<String,Object> data,String url){
		StringBuffer buf =null;
		if(!StringUtils.isEmpty(url)){
			buf= new StringBuffer(url);
			Set<String> keySet = data.keySet();
            for(String key : keySet){
                String value = data.get(key)+"";
                buf.append("&").append(key).append("=").append(value);
            }

			String _url=buf.toString();
			_url=_url.replaceFirst("&", "?");
			return _url;
		}
		return "";
	}
	
	
	/**
	 * 获取请求的url路径
	 */
	public static String getRequestUrl(HttpServletRequest request){
		String queryString = request.getQueryString();
		StringBuffer buf = new StringBuffer();
		buf.append(request.getRequestURI());
		if(!StringUtils.isEmpty(queryString)){
			buf.append("?").append(queryString);
		}
		return buf.toString();
	}

	
	/**
	 * 是否包含强制更新字段，如果存在那么替换
	 * @param request
	 * @return
	 */
	public static String replaceForceUpdate(HttpServletRequest request){
		String callback = request.getParameter(ConstantUtils.CALLBACK);
		String _ = request.getParameter(ConstantUtils._);
		
		callback = ConstantUtils.CALLBACK + "=" + (StringUtils.isEmpty(callback)? "" :callback);
		_ = ConstantUtils._ + "=" + (StringUtils.isEmpty(_)? "" :_);
		
		String url = getRequestUrl(request);
		url = url.replace("?"+ConstantUtils.FORCE_UPDATE,"").replace("&"+ConstantUtils.FORCE_UPDATE,"");
		url = url.replace("?"+callback,"").replace("&"+callback,"");
		url = url.replace("?"+_,"").replace("&"+_,"");
		return url;
	}
	
	
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes()).getRequest(); 
		return request;
	}
	
	
	public static HttpServletResponse getResponse(){
		HttpServletResponse response = ((ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
}
