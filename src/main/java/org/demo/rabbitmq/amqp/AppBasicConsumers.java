package org.demo.rabbitmq.amqp;

import java.util.Map;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class AppBasicConsumers {

	public static void main(String[] args) throws Exception {
	    Tool.log("Getting channel...");
		Channel channel = Tool.getChannel();
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

	    BasicConsumer1Lambdas.consume(channel, "queue-test1");
		
	    // NB : do not close
		// Tool.close(channel);
	}
}
