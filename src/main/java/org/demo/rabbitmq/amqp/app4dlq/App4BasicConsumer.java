package org.demo.rabbitmq.amqp.app4dlq;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class App4BasicConsumer {

	public static void main(String[] args) throws Exception {
		Tool.log("Creating connection...");
		Connection connection = Tool.createConnection();
//	    Tool.log("Current thread : " + Thread.currentThread().getName() 
//	    		+ "(" + Thread.currentThread().getId() + ")" );

		Tool.log("Creating channel...");
		Channel channel = connection.createChannel();

		Tool.log("Starting consumer...");
		// BasicConsumerWithReject.consume(channel1, Names.QUEUE_C);

		channel.basicConsume(Names.QUEUE_C, new ConsumerWithReject(channel));
		
		Tool.log("Consumer started.");
		
		// NB : do not close
		// Tool.close(channel);
	}
}
