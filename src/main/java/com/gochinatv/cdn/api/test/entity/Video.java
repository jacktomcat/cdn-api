package com.gochinatv.cdn.api.test.entity;

public class Video implements Cloneable{

	private int id;
	private String name;
	private String duration;
	private Album album;

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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		System.out.println(result);
		return result;
	}

	// http://www.cnblogs.com/lulipro/p/5628750.html
	@Override
	public boolean equals(Object obj) {
		if (this == obj)// 传入的比较对象的引用和this做比较，这样做是为了 save time ，节约执行时间,相等说明同一个引用
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video o = (Video) obj;
		if (id != o.getId())
			return false;
		if (!name.equals(o.getName()))
			return false;
		if (!duration.equals(o.getDuration()))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Video o = null;
		try {
			o = (Video) super.clone();
			//这里实现深度clone，否则clone出来的对象的引用是同一个
			o.album = (Album) album.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
