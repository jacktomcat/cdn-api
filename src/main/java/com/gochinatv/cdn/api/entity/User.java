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

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User){
			User o = (User)obj;
			if(this.getId() == o.getId() && this.getAge()==o.getAge() && this.getPhone().equals(o.getPhone()) &&
					this.getUserName().equals(o.getUserName())){
				System.out.println("========equals========yes");
				return true;
			}
		}
		System.out.println("========equals========no");
		return false;
	}

	/*@Override
	public int hashCode() {
		System.out.println("hashcode====="+super.hashCode());
		return super.hashCode();
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + age;
        System.out.println("hashcode====="+result);
        return result;
	}
	
}
