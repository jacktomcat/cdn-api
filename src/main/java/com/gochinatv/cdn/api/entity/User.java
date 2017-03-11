package com.gochinatv.cdn.api.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlRootElement
@JsonIgnoreProperties(value={"age"})
public class User{

	private int id;
	
	private String userName;
	
	private int age;
	
	private String phone;

	public User(){}
	
	public User(int id,String userName,int age,String phone){
		this.id = id;
		this.userName = userName;
		this.age = age;
		this.phone = phone;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlTransient
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
