package com.gochinatv.cdn.api.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.gochinatv.cdn.api.commons.HttpParamsUtils;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.service.CdnEncryptionService;


@Controller
@RequestMapping("/v1")
public class CdnEncryptionController extends BaseHandler{
    
	private Logger logger = LoggerFactory.getLogger(CdnEncryptionController.class);
	
	@Autowired
	private CdnEncryptionService cdnEncryptionService;
	
	
	@RequestMapping("/benchmark")
    @ResponseBody
	public JSONObject benchmark() {
		JSONObject result = null;
		try {
			result = success(result);
		}catch(Exception e){
			result = failure(e.getMessage());
		}
		return result;
	}
	
    /**
     * 获取动态防盗链的码
     * @param token
     * @return
     */
    @RequestMapping("/getCdnKey")
    @ResponseBody
	public JSONObject getCdnKey(String token, String callback) {
		JSONObject result = null;
		try {
			result = cdnEncryptionService.getCdnKey(token);
			result = success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			result = failure(e.getMessage());
		}
		return result;
	}
    
    
    
    /**
     * 获取动态防盗链的码
     * @param url
     * @param st 过期时间单位秒
     * @param callback
     * @return
     */
    @RequestMapping("/getPlayUrl")
    @ResponseBody
	public JSONObject getPlayUrl(@RequestParam(value = "url") String url,
			                     @RequestParam(value = "st", defaultValue="18000") int st,
			                     String callback) {
		JSONObject result = null;
		try {
			if(StringUtils.isEmpty(url)){
				result = failure("Invalid url");
			}else{
				String format_url = url.toLowerCase();
				if((format_url.indexOf("http://")!=-1 || format_url.indexOf("https://")!=-1) && (format_url.indexOf(".m3u8")!=-1 || format_url.indexOf(".mp4")!=-1)){
					result = cdnEncryptionService.getPlayUrl(url,st);
					result = success(result);
				}else{
					result = failure("Invalid url");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			result = failure(e.getMessage());
		}
		resultAdd(callback, result);
		return null;
	}

    
    private void resultAdd(String callback, JSONObject value) {
        HttpServletResponse response = HttpParamsUtils.getResponse();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String result = value.toJSONString();
            if (!StringUtils.isEmpty(callback)) {
                result = String.format(callback + "(%s)", value);
            }
            out.print(result);
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
	
}
