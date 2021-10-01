package org.demo.rabbitmq.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class AppBasicPublish {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.createChannel();
		
		// basicPublish() parameters :
		// . exchange   : the exchange to publish the message to ( or "" for "default exchange" )
		// . routingKey : the routing key (
		// . properties : other properties for the message - routing headers etc
		// . body       : the message body

//	    // Basic publish with "default exchange" (just a queue is enough, no exchange, no binding) 
//	    // The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. 
//	    // It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
//		for (int i = 1; i <= 3; i++) {
//			String message = "My message via default exchange" + i;
//			// "" is for "default exchange"
//			// queue name is used instead of routing key
//			channel.basicPublish("", "queue-test0", null, message.getBytes());
//		}
		
		// Basic publish with explicit exchange name
		Tool.log("Publishing messages ");
		for (int i = 1; i <= 40; i++) {
			
			String message = "My message #" + i;
			String routingKey = Names.ROUTING_KEY1 ;
			if ( i % 2 == 0 ) {
				routingKey = Names.ROUTING_KEY2 ;
			}
			AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
	            .contentType("text/plain")
	            .deliveryMode(2)
	            .priority(1)
	            .userId("guest") // must be the authenticated user
	            .build();
		    // publish with (exchange, routingKey, properties, messageBody)
		    channel.basicPublish(Names.EXCHANGE_DIRECT1, routingKey, prop, message.getBytes());
		}
		Tool.log("Messages published.");
		// if invalid exchange name : ShutdownSignalException : no exchange 'xxxx' in vhost '/',
		
		Tool.close(channel);
	}
}
