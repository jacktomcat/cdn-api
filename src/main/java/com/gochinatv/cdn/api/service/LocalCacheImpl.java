package com.gochinatv.cdn.api.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class LocalCacheImpl {
   
	private Path importPath;
	private LoadingCache<Integer, String> keyValueCache;
	
	public void setImportPath(String importPath) {
		this.importPath = Paths.get(importPath);
	}

	public void init(){
		keyValueCache = CacheBuilder.newBuilder()
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, String>() {
					@Override
					public String load(Integer id) throws Exception {
						System.out.println("***执行方法走内存***");
						return id+"#"+"---"+importPath.toString();
					}
				});
	}
	
	public String getValue(Integer id){
		String result = null;
		try {
			result = keyValueCache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}
}
