package com.gochinatv.cdn.api.framework;

public class HookJVMThread extends Thread{
   
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new HookJVMThread());
		for(int i=0;i<10;i++){ 
	        System.out.println("i="+i); 
	       if(i==4){ 
	           System.exit(0); //在i==4的时候虚拟机退出，会执行注册的hook线程，而不会执行下面的打印语句
	           System.out.println("hook");
	       } 
	         try { 
	             Thread.sleep(1000); 
	       } catch (InterruptedException e) { 
	       }
		}
	}

	@Override
	public void run() {
		System.out.println("hook shutdown!");
	}
	
}
