package org.demo.rabbitmq.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class BasicPublish {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.getChannel();
		
		// Create an exchange (no error if already exists)
		Tool.log("Creating exchange...");
	    channel.exchangeDeclare("my-direct-exchange", BuiltinExchangeType.DIRECT, true);
		//Tool.pause("Exchange created.");
	     
		// Create queues (no error if already exists)
		Tool.log("Creating queues...");
	    channel.queueDeclare("queue-test0", true, false, false, null);
	    channel.queueDeclare("queue-test1", true, false, false, null);
	    channel.queueDeclare("queue-test2", true, false, false, null);
	    channel.queueDeclare("queue-test3", true, false, false, null);
		//Tool.pause("Queues created.");

		// basicPublish() parameters :
		// . exchange   : the exchange to publish the message to ( or "" for "default exchange" )
		// . routingKey : the routing key (
		// . properties : other properties for the message - routing headers etc
		// . body       : the message body

	    // Basic publish with "default exchange" (just a queue is enough, no exchange, no binding) 
	    // The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. 
	    // It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
		for (int i = 1; i <= 3; i++) {
			String message = "My message via default exchange" + i;
			// "" is for "default exchange"
			// queue name is used instead of routing key
			channel.basicPublish("", "queue-test0", null, message.getBytes());
		}
		
		// Create queue - exchange binding (no error if already exists)
		Tool.log("Binding queue-exchange");
		// Parameters :
		// . queue the name of the queue
		// . exchange the name of the exchange
		// . routingKey the routing key to use for the binding
		channel.queueBind("queue-test1", "my-direct-exchange", "my-key12");
		channel.queueBind("queue-test2", "my-direct-exchange", "my-key12");
		//Tool.pause("Binding created.");

		// Basic publish with explicit exchange name
		Tool.log("Publishing messages ");
		for (int i = 1; i <= 40; i++) {
			String message = "My super message via exchange #" + i;
			AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
	            .contentType("text/plain")
	            .deliveryMode(2)
	            .priority(1)
	            .userId("guest") // must be the authenticated user
	            .build();
		    // publish with (exchange, routingKey, properties, messageBody)
		    channel.basicPublish("my-direct-exchange", "my-key12", prop, message.getBytes());
		}
		Tool.log("Messages published.");
		// if invalid exchange name : ShutdownSignalException : no exchange 'xxxx' in vhost '/',

		
		Tool.close(channel);
	}
}
