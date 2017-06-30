package com.gochinatv.cdn.api.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * expireAfterAccess:最后一次访问过期后不走缓存
 * expireAfterWrite:第一次写入到过期后不走缓存
 * @author tingyun
 *
 */
public class LocalCacheImpl {

	private Path importPath;
	private LoadingCache<Integer, String> keyValueCache;
	private int expire;
	

	public void setImportPath(String importPath) {
		this.importPath = Paths.get(importPath);
	}
	
	public void setExpire(int expire) {
		this.expire = expire;
	}

	public void init() {
		keyValueCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, String>() {
					@Override
					public String load(Integer id) throws Exception {
						System.out.println("***执行方法走内存***");
						return id + "#" + "---" + importPath.toString();
					}
		});
		
		System.out.println(expire+"");
	}

	public String getValue(Integer id) {
		String result = null;
		try {
			result = keyValueCache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static LoadingCache<Integer, String> keysValueCache;
	static{
		
		keysValueCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, String>() {
					@Override
					public String load(Integer id) throws Exception {
						System.out.println("***执行方法走内存***");
						return id + "#" + "---";
					}
		});
	}
	
	public String getValues(Integer id) {
		String result = null;
		try {
			result = keysValueCache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		final LocalCacheImpl impl = new LocalCacheImpl();
		
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
		pool.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String result = impl.getValues(2);
				System.out.println(result);
			}
		}, 0, 5, TimeUnit.SECONDS);
		
	}
	
}
