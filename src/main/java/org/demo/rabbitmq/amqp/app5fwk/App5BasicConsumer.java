package org.demo.rabbitmq.amqp.app5fwk;

import org.demo.rabbitmq.amqp.commons.Tool;
import org.demo.rabbitmq.amqp.fwk.RabbitLogger;

import com.rabbitmq.client.Connection;

public class App5BasicConsumer {

	public static void main(String[] args) throws Exception {
		Tool.log("Creating connection...");
		Connection connection = Tool.createConnection();

		RabbitLogger.setOutput(System.out);
		Tool.log("Starting consumer...");
		
		new ConsumerQueueC(connection).consume();
		
		Tool.log("Consumer started.");
		
		// NB : do not close
		// Tool.close(channel);
	}
}
