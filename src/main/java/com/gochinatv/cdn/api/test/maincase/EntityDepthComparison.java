package com.gochinatv.cdn.api.test.maincase;

import java.util.HashSet;
import java.util.Set;

import com.gochinatv.cdn.api.test.entity.Album;
import com.gochinatv.cdn.api.test.entity.Video;

public class EntityDepthComparison {
   
	
	public static void main(String[] args) throws Exception {
		Set<Video> videos = new HashSet<>();
		Set<Album> albums = new HashSet<>();
		
		Album album1 = new Album(); 
		album1.setId(123);
		album1.setName("album1");
		
		Video video1 = new Video();
		video1.setId(1);
		video1.setName("video1");
		video1.setAlbum(album1);
		video1.setDuration("30");
		
		
		Album album2 = new Album(); 
		album2.setId(123);
		album2.setName("album3");
		
		Video video2 = new Video();
		video2.setId(1);
		video2.setAlbum(album2);
		video2.setName("video1");
		video2.setDuration("30");
		
		videos.add(video1);
		videos.add(video2);
		
		albums.add(album1);
		albums.add(album2);
		
		
		Video video3 = (Video)video2.clone();
		video2.setName("after-video2");
		video3.setName("after-video3");
		video3.getAlbum().setName("after-album3");
		
		System.out.println("albums:"+albums.size());
		System.out.println("videos:"+videos.size());
		
		
		System.out.println("clone album2 to album3");
		
		//这里video2.getAlbum().getName() 只有video里面的album实现了深度clone指向的引用才不是一个引用
		System.out.println("video2 name:"+video2.getName()+"---album name ::"+video2.getAlbum().getName());
		System.out.println("video3 name:"+video3.getName()+"---album name ::"+video3.getAlbum().getName());
	}
	
}
