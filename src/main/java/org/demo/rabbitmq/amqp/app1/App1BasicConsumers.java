package org.demo.rabbitmq.amqp.app1;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class App1BasicConsumers {

	public static void main(String[] args) throws Exception {
	    Tool.log("Creating connection...");
	    Connection connection = Tool.createConnection();
	    Tool.log("Current thread : " + Thread.currentThread().getName() 
	    		+ "(" + Thread.currentThread().getId() + ")" );

	    Tool.log("Starting consumers...");
	    

//	    Tool.log("channel.basicConsume(..)...");
//		// . queue the name of the queue
//		// . autoAck true if the server should consider messages acknowledged once delivered; 
//		//          false if the server should expect explicit acknowledgements
//		// . deliverCallback callback when a message is delivered
//		// . cancelCallback callback when the consumer is cancelled
//		String consumerTag = channel.basicConsume("queue-test1", true, deliverCallback, cancelCallback);
//		// channel.basicConsume : creates a thread pool and wait for messages 
//	    Tool.log("Consumer tag : " + consumerTag);
//
//		// if invalid exchange name : ShutdownSignalException : no exchange 'xxxx' in vhost '/',

	    Channel channel1 = connection.createChannel();
	    BasicConsumer1Lambdas.consume(channel1, Names.QUEUE1);
	    	    
	    Channel channel2 = connection.createChannel();
	    BasicConsumer1Lambdas.consume(channel2, Names.QUEUE2);
		
	    // NB : do not close
		// Tool.close(channel);
	}
}
