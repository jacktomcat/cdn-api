package com.gochinatv.cdn.api.framework;


import com.gochinatv.cdn.api.commons.ConstantUtils;
import com.gochinatv.cdn.api.commons.HttpParamsUtils;
import com.gochinatv.cdn.api.controller.base.BaseHandler;
import com.gochinatv.cdn.api.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @作者 zhuhh
 * @描述 控制层缓存拦截
 * @创建时间 2015年10月14日 上午9:44:10
 * @修改时间
 */
@Component
public class ControllerInterceptor extends BaseHandler implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    @Autowired
    private RedisService redisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String force_update = request.getParameter(ConstantUtils.FORCE_UPDATE_TRUE);
        if (!StringUtils.isEmpty(force_update)) {//强制更新，放过
            return true;
        }

        response.setContentType("application/json;charset=utf-8");
        String url = HttpParamsUtils.replaceForceUpdate(request);
        url = String.format(ConstantUtils.SECURITY_PLAY_URL, url);
        try {
        	String content = redisService.get(url);
            if (!StringUtils.isEmpty(content)) {
                PrintWriter out = response.getWriter();
                
                content = success(content);
                
                String callback = request.getParameter(ConstantUtils.CALLBACK);
                if (!StringUtils.isEmpty(callback)){
                	content = String.format(callback + "(%s)", content);
                }
                out.print(content);
                return false;
            }
        } catch (Exception e) {
            logger.error("******************获取缓存失败：{}****************", e.getMessage());
        }
        return true;
    }

    
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
      
    }

}
