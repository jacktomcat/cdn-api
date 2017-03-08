package com.gochinatv.cdn.api.elasticsearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
	 
	private String host;
	private String dbname;
	private String dbport;
	private String userName;
	private String password;
	
	public DBUtils(String host,String dbname,String dbport,String userName,String password){
		this.host=host;
		this.dbname=dbname;
		this.dbport=dbport;
		this.userName=userName;
		this.password=password;
	}
	
	public String getHost() {
		return host;
	}
 
	public void setHost(String host) {
		this.host = host;
	}
 
	public String getDbname() {
		return dbname;
	}
 
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
 
	public String getDbport() {
		return dbport;
	}
 
	public void setDbport(String dbport) {
		this.dbport = dbport;
	}
 
	public String getUserName() {
		return userName;
	}
 
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
 
	public Connection getConnection() {
		Connection conn = null ;
		String driver = "com.mysql.jdbc.Driver";
		String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8", getHost(),getDbport(),getDbname());
		try {
			Class.forName(driver);
		    conn = DriverManager.getConnection(url, getUserName(), getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public List<Video> getResultSet(int min,int max) {
		List<Video> list = new ArrayList<Video>();
		try {
			Connection conn = getConnection();
			Statement statement = conn.createStatement();
			String sql = "SELECT * FROM `VIDEO` limit "+min+","+max;
		    ResultSet rs = statement.executeQuery(sql);
		    while(rs.next()){
		    	Video video = new Video();
		    	video.setId(rs.getLong("id"));
		    	video.setAlbumId(rs.getLong("album_id"));
		    	video.setSrc(rs.getString("src"));
		    	video.setName(rs.getString("name"));;
		    	video.setSubname(rs.getString("subname"));
		    	//video.setInstallments(rs.getString("installments"));
		    	video.setDescription(rs.getString("duration"));
		    	//video.setStandardPic(rs.getString("standard_pic"));
		    	video.setDescription(rs.getString("description"));
		    	//video.setTag(rs.getString("tag"));
		    	//video.setVedioType(rs.getInt("vedio_type"));
		    	//video.setIsdisplay(rs.getInt("isdisplay"));
		    	//video.setDisplayOrder(rs.getInt("display_order"));
		    	video.setCreateTime(rs.getDate("create_time"));
		    	video.setModifyTime(rs.getDate("modify_time"));
		    	video.setCnName(rs.getString("cn_name"));
		    	list.add(video);
			}
			rs.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
    
}
