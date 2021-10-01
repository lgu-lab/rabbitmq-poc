package org.demo.rabbitmq.amqp.app2;

import org.demo.rabbitmq.amqp.Names;
import org.demo.rabbitmq.amqp.Tool;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class App2BasicPublish {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.createChannel();
		
		// Basic publish with explicit exchange name
		Tool.log("Publishing messages ");
		int max = 10;
		for (int i = 1; i <= max; i++) {
			
			String message = "My message #" + i;
			String routingKey = Names.ROUTING_KEY_A ;
//			if ( i % 2 == 0 ) {
//				//routingKey = Names.ROUTING_KEY2 ;
//			}
			AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
					//.expiration("10000") // 10 seconds
					.build();
//	            .contentType("text/plain")
//	            .deliveryMode(2)
//	            .priority(1)
//	            .userId("guest") // must be the authenticated user
	            
		    // publish with (exchange, routingKey, properties, messageBody)
		    channel.basicPublish(Names.EXCHANGE_QUEUE_A, routingKey, prop, message.getBytes());
		}
		Tool.log(max + " messages published.");
		
		Tool.close(channel);
	}
}
