package com.gochinatv.cdn.api.framework;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gochinatv.cdn.api.service.LocalCacheImpl;


@Component
public class HookThreadService {
   
	@Autowired
	private LocalCacheImpl localCacheImpl;
	
	
	@PostConstruct
	public void init() {
		//在执行tomcat ./shutdown.sh的时候会调用这里的方法勾子函数
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
                  System.out.println("****************************");		
                  System.out.println("*********退出系统**********");		
                  System.out.println("****************************");	
                  //TODO 这里有个bug 在退出之后调用这里的service报错：java.lang.NoClassDefFoundError
                  //目前找到的与原因是调用shtudown会导致类的卸载，所以这里会报错，但是没有得到验证
                  System.out.println("退出之前调用localCacheImpl（100）："+localCacheImpl.getValue(100));
			}
		}));
	}
}
