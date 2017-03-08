package com.gochinatv.cdn.api.controller.base;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSONObject;


public class BaseHandler {
	
	/**
	 * 返回成功
	 * @return
	 */
	public JSONObject success(){
		JSONObject obj = new JSONObject();
		obj.put("code", "1");
		obj.put("message", "操作成功");
		obj.put("ts", System.currentTimeMillis());
		return obj;
	}
	
	/**
	 * 缓存并返回
	 * @param data
	 * @return
	 */
	public JSONObject success(Object data) {
		JSONObject obj = this.success();
		obj.put("data", data);
		return obj;
	}
	
	/**
	 * 缓存并返回
	 * @param data
	 * @return
	 */
	public String success(String data) {
		JSONObject obj = this.success();
		obj.put("data", JSONObject.parse(data));
		return obj.toJSONString();
	}
	
	/**
	 * 缓存并返回
	 * @param data
	 * @return
	 */
	public JSONObject success(Object data,Map<String,Object> params) {
		JSONObject obj = this.success(data);
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			obj.put(entry.getKey(),entry.getValue());
		}
		return obj;
	}
	
	
	/**
	 * 操作失败
	 * @param message
	 * @return
	 */
	public JSONObject failure(String message){
		JSONObject obj = new JSONObject();
		obj.put("code", "0");
		obj.put("message", "操作失败:"+message);
		obj.put("ts",System.currentTimeMillis());
		return obj;
	}
	
}
