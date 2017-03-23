package com.gochinatv.cdn.api.service;

import java.util.HashSet;
import java.util.Set;

import com.gochinatv.cdn.api.entity.User;

public class TestSetUser {
   
	/**
	 * 判断set中的 PO 有没有重复，需要重写PO中的 equals和hashcode方法
	 * @param args
	 */
	public static void main(String[] args) {
		Set<User> set = new HashSet<User>();
		
		User u1 = new User();
		u1.setId(1);
		u1.setAge(12);
		u1.setPhone("1");
		u1.setUserName("张三");
		
		User u2 = new User();
		u2.setId(1);
		u2.setAge(12);
		u2.setPhone("1");
		u2.setUserName("张三");
		
		System.out.println(set.add(u1));//如果不存在，添加则返回true
		System.out.println(set.add(u2));//如果存在，添加则返回false
		
		System.out.println(set.size());
	}
}
