package org.demo.rabbitmq.amqp.app4dlq;

import java.io.IOException;

import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class BasicConsumerWithReject {

	public static void sleep(long n) {
	    try {
			Thread.sleep(1000L) ;
		} catch (InterruptedException e) {
		    Tool.log("InterruptedException : " + e.getMessage());
		}
	}
	
	public static String consume(Channel channel, String queueName) throws IOException {
	    Tool.log("Starting consumer...");
	    Tool.log("Current thread : " + Thread.currentThread().getName() 
	    		+ "(" + Thread.currentThread().getId() + ")" );
	    
	    //------------------------------------------------------------------------------------
		// DeliverCallback : Callback interface to be notified when a message is delivered
	    // ( @FunctionalInterface ) for a lambda-oriented syntax
		DeliverCallback deliverCallback = (consumerTag, message) -> {
		    Tool.log("-----");
		    Tool.log("DeliverCallback : consumerTag = " + consumerTag );
		    Tool.log("DeliverCallback : Current thread : " + Thread.currentThread().getName() 
		    		+ "(" + Thread.currentThread().getId() + ")" );
		    // pool-1-thread-2, pool-1-thread-3, pool-1-thread-4
		    
//		    Tool.printMessageBody(message);
//		    Tool.printMessageProperties(message);
		    Tool.printMessage(message);
		    String messageBody = Tool.messageBodyAsString(message);
		    
		    if ( messageBody.contains("OOPS") ) {
		    	
		    }
		    
		    Tool.log("DeliverCallback : processing message..." );
			sleep(1000L) ; // simulation for message processing duration
		};
		
	    //------------------------------------------------------------------------------------
		// CancelCallback : Callback interface to be notified of the cancellation of a consumer
	    // ( @FunctionalInterface ) for a lambda-oriented syntax
	    CancelCallback cancelCallback = (consumerTag) -> {
	    	System.out.println(consumerTag);
	    };		

	    //------------------------------------------------------------------------------------
	    Tool.log("channel.basicConsume("+queueName+")...");
		// . queue the name of the queue
		// . autoAck true if the server should consider messages acknowledged once delivered; 
		//          false if the server should expect explicit acknowledgements
		// . deliverCallback callback when a message is delivered
		// . cancelCallback callback when the consumer is cancelled
		String consumerTag = channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
		// channel.basicConsume : creates a thread pool and wait for messages 
	    Tool.log("Consumer tag : " + consumerTag);
	    return consumerTag;
	}
}
