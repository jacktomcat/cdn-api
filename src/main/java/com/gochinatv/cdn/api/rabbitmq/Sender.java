package com.gochinatv.cdn.api.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    
	static ConnectionFactory factory = null;
	
	public static ConnectionFactory getConnectionFactory(){
		if(factory==null){
			factory = new ConnectionFactory();
			factory.setUsername("");
			factory.setPassword("");
			factory.setVirtualHost("/");
			factory.setHost("");
			factory.setPort(5176);
		}
		return factory;
	}
	
	
	public static Connection getConnection(){
		ConnectionFactory factory = getConnectionFactory();
		Connection conn = null;
		try {
			conn = factory.newConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static void sendMessage(){
		Connection conn = getConnection();
		try {
			Channel channel = conn.createChannel();
			
			channel.exchangeDeclare(exchangeName, "direct", true);
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchangeName, routingKey);
			
			channel.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//Channel channel = conn.createChannel();
	}
}
