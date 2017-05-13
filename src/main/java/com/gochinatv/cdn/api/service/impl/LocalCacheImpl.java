package com.gochinatv.cdn.api.service.impl;

import java.io.IOException;
import java.nio.file.Files;
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
	private String export;
	private String exportHour;
	private String exportWeek;

	public void setImportPath(String importPath) {
		this.importPath = Paths.get(importPath);
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getExportHour() {
		return exportHour;
	}

	public void setExportHour(String exportHour) {
		this.exportHour = exportHour;
	}

	public String getExportWeek() {
		return exportWeek;
	}

	public void setExportWeek(String exportWeek) {
		this.exportWeek = exportWeek;
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
		
		String[] dirs = new String[]{this.getExport(),this.getExportHour(),this.getExportWeek()};
		for (String dir : dirs) {
			Path path = Paths.get(dir);
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
}
