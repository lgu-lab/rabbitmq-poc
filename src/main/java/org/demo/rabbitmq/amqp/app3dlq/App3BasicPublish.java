package org.demo.rabbitmq.amqp.app3dlq;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class App3BasicPublish {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.createChannel();
		
		// Basic publish with explicit exchange name
		Tool.log("Publishing messages ");
		int max = 10;
		for (int i = 1; i <= max; i++) {
			
			String message = "My message #" + i + " (routing key = '" + Names.ROUTING_KEY_B + "')";
			String routingKey = Names.ROUTING_KEY_B ;
//			if ( i % 2 == 0 ) {
//				//routingKey = Names.ROUTING_KEY2 ;
//			}
			AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
					//.expiration("10000") // 10 seconds
					.build();
	            
		    // publish with (exchange, routingKey, properties, messageBody)
		    channel.basicPublish(Names.EXCHANGE_QUEUE_B, routingKey, prop, message.getBytes());
		}
		Tool.log(max + " messages published.");
		
		Tool.close(channel);
	}
}
