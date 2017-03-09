package com.gochinatv.cdn.api.tasks;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
/**
 * 1、此处如果不设置[[@Lazy]] 那么该bean会初始化，并且系统一初始化调用 [[@PostConstruct]]注解的方法
 * 2、如果设置为[[@Lazy(true)]] 那么bean不会被初始化，直到第一次调用该bean的时候才会被初始化
 * 3、如果设置为[[@Lazy(false)]] 那么该bean会初始化,而且是单利模式
 *  ```If present and set to false, 
 *   the bean will be instantiated on startup by bean factories that perform eager initialization of singletons```
 *   
 * @author zhuhh
 *
 */
@Lazy(false)
public class VideoTask {

	@PostConstruct
	public void init() {
		System.out.println("========init==================");
	}

	@PreDestroy
	public void dostory() {
		System.out.println("I'm  destory method  using  @PreDestroy.....");
	}
}
