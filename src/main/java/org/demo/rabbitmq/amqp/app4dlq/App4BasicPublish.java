package org.demo.rabbitmq.amqp.app4dlq;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class App4BasicPublish {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.createChannel();
		
		// Basic publish with explicit exchange name
		Tool.log("Publishing messages ");
		int max = 20;
		for (int i = 1; i <= max; i++) {
			
			String message = "My message #" + i + " (routing key = '" + Names.ROUTING_KEY_C + "')";
			String routingKey = Names.ROUTING_KEY_C ;
			if ( i % 2 == 0 ) {
				message = message + " OOPS!";
			}
			else {
				if ( i % 3 == 0 ) {
					// 3, 9, 15
					message = message + " ARGH!";  
				}
			}
			AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
					.build();
	            
		    // publish with (exchange, routingKey, properties, messageBody)
		    channel.basicPublish(Names.EXCHANGE_QUEUE_C, routingKey, prop, message.getBytes());
		}
		Tool.log(max + " messages published.");
		
		Tool.close(channel);
	}
}
