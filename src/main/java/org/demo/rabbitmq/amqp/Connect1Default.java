package org.demo.rabbitmq.amqp;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Connect1Default {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		System.out.println("ConnectionFactory created.");
		
		// Set default values for connections
//		factory.setHost("localhost");
//		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setConnectionTimeout(2000);
		// Convenience method for setting the fields in an AMQP URI: host, port, username, password and virtual host. 
		// If any part of theURI is omitted, the ConnectionFactory's corresponding variable is left unchanged.
//		factory.setUri("myhost");
		
		Connection connection ;
		// Connection with single address with default values : localhost, 5672, etc (or factory defaults)
//		connection = factory.newConnection("MyConnectionName");

		// Connection with single address with specific values 
//		Address[] address = { new Address("localhost", 5672) };
//		connection = factory.newConnection(address);
		
		// Connection with multiple addresses (only 2nd addr is valid)
		Address[] addresses = {new Address("192.168.1.4", 5672), new Address("localhost", 5672)};
		connection = factory.newConnection(addresses);
		
		System.out.println("Connection ready : ");
		// "id" : 
		// This ID must be unique, otherwise some services like the metrics collector won't work properly. 
		// This ID doesn't have to be provided by the client,
		// services that require it will be assigned automatically if not set.
		System.out.println(" unique id     : " + connection.getId()); // null (not set) 
		
		System.out.println(" port          : " + connection.getPort() ); // 5672
		System.out.println(" channel max   : " + connection.getChannelMax()); // 2047
		System.out.println(" clt prov name : " + connection.getClientProvidedName()); // MyConnectionName
		System.out.println(" heartbeat     : " + connection.getHeartbeat() ); // negotiated heartbeat interval
		System.out.println(" getFrameMax   : " + connection.getFrameMax() ); // negotiated maximum frame size
		Tool.pause();
		
		Channel channel = connection.createChannel();
		System.out.println("Channel ready : ");
		Tool.pause();

		System.out.println("Closing Channel... ");
		channel.close();
		System.out.println("Channel closed.");
		Tool.pause();

		System.out.println("Closing Connection... ");
		connection.close();
		System.out.println("Connection closed.");
	}	
}
