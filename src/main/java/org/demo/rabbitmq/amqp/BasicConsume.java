package org.demo.rabbitmq.amqp;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class BasicConsume {

	public static void sleep(long n) {
	    try {
			Thread.sleep(1000L) ;
		} catch (InterruptedException e) {
		    Tool.log("InterruptedException : " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception {
		Channel channel = Tool.getChannel();
	    Tool.log("Starting consumer...");
	    Tool.log("Current thread : " + Thread.currentThread().getName() 
	    		+ "(" + Thread.currentThread().getId() + ")" );
	    

		DeliverCallback deliverCallback = (consumerTag, message) -> {
		    Tool.log("DeliverCallback : consumerTag = " + consumerTag );
		    Tool.log("DeliverCallback : Current thread : " + Thread.currentThread().getName() 
		    		+ "(" + Thread.currentThread().getId() + ")" );
		    // pool-1-thread-2, pool-1-thread-3, pool-1-thread-4
		    Tool.log("DeliverCallback : message = " + new String(message.getBody(), "UTF-8") );
		    Tool.log("DeliverCallback : processing message..." );
			sleep(1000L) ;
		};
		
	    CancelCallback cancelCallback = (consumerTag) -> {
	    	System.out.println(consumerTag);
	    };		

	    Tool.log("channel.basicConsume(..)...");
		// . queue the name of the queue
		// . autoAck true if the server should consider messages acknowledged once delivered; 
		//          false if the server should expect explicit acknowledgements
		// . deliverCallback callback when a message is delivered
		// . cancelCallback callback when the consumer is cancelled
		String consumerTag = channel.basicConsume("queue-test1", true, deliverCallback, cancelCallback);
		// channel.basicConsume : creates a thread pool and wait for messages 
	    Tool.log("Consumer tag : " + consumerTag);

		// if invalid exchange name : ShutdownSignalException : no exchange 'xxxx' in vhost '/',
		
	    // NB : do not close
		// Tool.close(channel);
	}
}
