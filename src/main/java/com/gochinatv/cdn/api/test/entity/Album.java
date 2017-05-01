package com.gochinatv.cdn.api.test.entity;

public class Album implements Cloneable{

	private int id;
	private String name;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	//http://www.cnblogs.com/lulipro/p/5628750.html
	@Override
	public boolean equals(Object obj) {
		if (this == obj)//传入的比较对象的引用和this做比较，这样做是为了 save time ，节约执行时间,相等说明同一个引用
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album a = (Album)obj;
		if (id != a.getId())
			return false;
		if (name!=null && !name.equals(a.getName()))
			return false;
		if (title!=null && !title.equals(a.getTitle()))
			return false;
		return true;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Album o = null;
		try {
			o = (Album) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
