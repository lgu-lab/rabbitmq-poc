package org.demo.rabbitmq.amqp.commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

public class Tool {

	public static void println(String msg) {
		System.out.println(msg);
	}

	public static void log(String msg) {
		println("[LOG]" + msg);
	}

	private static final Scanner inScanner = new Scanner(System.in);

	public static void pause(String msg) {
		if (msg != null) {
			System.out.println(msg);
		}
		System.out.println("Press [ENTER] to continue... ");
		inScanner.nextLine();
	}

	public static void pause() {
		pause(null);
	}

	public static Connection createConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		log("ConnectionFactory created.");

//		Connection connection = factory.newConnection(Config.AMQP_URL);
		// Connection with default values : localhost, 5672
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection("MyConnectionName");
		log("Connection created.");
		return connection;
	}

	public static Channel createChannel() throws TimeoutException, IOException {
		Connection connection = createConnection();
		Channel channel = connection.createChannel();
		log("Channel created.");
		return channel;
	}

	public static void close(Channel channel) throws TimeoutException, IOException {
		Connection connection = channel.getConnection();
		channel.close();
		log("Channel closed.");
		connection.close();
		log("Connection closed.");
	}

	public static String messageBodyAsString(Delivery message) throws UnsupportedEncodingException {
		byte[] body = message.getBody();
		return new String(body, "UTF-8");
	}

	public static void printMessage(Delivery message) throws UnsupportedEncodingException {
		printMessageBody(message);
		printMessageProperties(message);
	}

	public static void printMessageBody(Delivery message) throws UnsupportedEncodingException {
		printMessageBody(message.getBody());
	}

	public static void printMessageBody(byte[] body) throws UnsupportedEncodingException {
		printMessageBody( new String(body, "UTF-8") );
	}
	public static void printMessageBody(String body) throws UnsupportedEncodingException {
		println(". message body : " + body );
	}

	public static void printMessageProperties(Delivery message) {
		printMessageProperties(message.getProperties());
	}

	public static void printMessageProperties(BasicProperties properties) {
		// Spec AMQP : "Immutable properties of the message. MUST remain unaltered"
		// BasicProperties : getXxx only (no setXxx )
		if (properties != null) {
			println(". message properties : ");
			println(" . Delivery Mode : " + properties.getDeliveryMode()); // Integer
			println(" . Expiration    : " + properties.getExpiration()); // String
			println(" . Priority      : " + properties.getPriority()); // Integer
			println(" . User Id       : " + properties.getUserId()); // String

			println(" . Application Id   : " + properties.getAppId()); // String
			println(" . Content Encoding : " + properties.getContentEncoding()); // String
			println(" . Content Type     : " + properties.getContentType()); // String
			println(" . Correlation Id   : " + properties.getCorrelationId()); // String

			Map<String, Object> headers = properties.getHeaders();
			if (headers != null) {
				println(". message headers : ");
				for (Map.Entry<String, Object> entry : headers.entrySet()) {
					println(" . '" + entry.getKey() + "' : " + entry.getValue());
				}
			} else {
				println(". message headers : no headers (null)");
			}
		} else {
			println(". message properties : no properties (null)");
		}
	}

	public static void sleep(long n) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			Tool.log("InterruptedException : " + e.getMessage());
		}
	}

}
