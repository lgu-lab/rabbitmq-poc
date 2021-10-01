package org.demo.rabbitmq.amqp;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class BasicConsumer1Lambdas {

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
		    byte[] body = message.getBody();
		    Tool.log("DeliverCallback : message = " + new String(body, "UTF-8") );
		    BasicProperties msgProp = message.getProperties();
		    // Spec AMQP : "Immutable properties of the message. MUST remain unaltered"
		    // BasicProperties : getXxx only (no setXxx )
		    if ( msgProp != null ) {
			    Tool.log(" . message Priority : " + msgProp.getPriority() ); // Integer
			    Tool.log(" . message ContentType : " + msgProp.getContentType() ); // String
			    Tool.log(" . message UserId : " + msgProp.getUserId() ); // String

			    Map<String,Object> headers = msgProp.getHeaders();
			    if ( headers != null ) {
				    Tool.log(". message headers : " );
				    for (Map.Entry<String, Object> entry : headers.entrySet()) {
				    	Tool.log("   ." + entry.getKey()+" : "+entry.getValue());
				    }
			    }
			    else {
				    Tool.log("DeliverCallback : no headers " );
			    }
		    }
		    else {
			    Tool.log("DeliverCallback : no properties " );
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
