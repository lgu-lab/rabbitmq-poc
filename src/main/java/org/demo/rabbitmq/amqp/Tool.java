package org.demo.rabbitmq.amqp;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Tool {
	
	public static void log(String msg) {
		System.out.println("[LOG]" + msg);
	}
	
	private static final Scanner inScanner = new Scanner(System.in);	
	public static void pause(String msg) {
		if ( msg != null ) {
			System.out.println(msg);
		}
		System.out.println("Press [ENTER] to continue... ");
		inScanner.nextLine();
	}
	public static void pause() {
		pause(null);
	}
	
	public static Connection createConnection() throws IOException, TimeoutException  {
		ConnectionFactory factory = new ConnectionFactory();
		log("ConnectionFactory created.");
		
//		Connection connection = factory.newConnection(Config.AMQP_URL);
		// Connection with default values : localhost, 5672
		Connection connection = factory.newConnection("MyConnectionName");
		log("Connection created.");
		return connection;
	}

	public static Channel createChannel() throws TimeoutException, IOException {
		Connection connection = createConnection();
		Channel channel = connection.createChannel();
		log("Channel created.");
		return channel ;
	}
	
	public static void close(Channel channel) throws TimeoutException, IOException {
		Connection connection = channel.getConnection();
		channel.close();
		log("Channel closed.");
		connection.close();
		log("Connection closed.");
	}
}
