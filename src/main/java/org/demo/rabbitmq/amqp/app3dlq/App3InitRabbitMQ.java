package org.demo.rabbitmq.amqp.app3dlq;

import java.util.HashMap;
import java.util.Map;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class App3InitRabbitMQ {

	public static void main(String[] args) throws Exception {
		Channel channel = Tool.createChannel();
		
	    //------------------------------------------------------------------------
	    // EXCHANGE(S)
	    //------------------------------------------------------------------------
		// Create an exchange (no error if already exists)
		Tool.log("Declaring exchange(s)...");
		// exchangeDeclare 
		// Parameters:
		//  . exchange the name of the exchange
		//  . type the exchange type
		//  . durable true if we are declaring a durable exchange (the exchange will survive a server restart)
		// Throws: java.io.IOException - if an error is encountered
	    channel.exchangeDeclare(Names.EXCHANGE_QUEUE_B, BuiltinExchangeType.DIRECT, true);
	    channel.exchangeDeclare(Names.EXCHANGE_DLQ_B,   BuiltinExchangeType.DIRECT, true);
	     
	    //------------------------------------------------------------------------
	    // QUEUE(S)
	    //------------------------------------------------------------------------
		// Create queues (no error if already exists)
		Tool.log("Declaring queue(s)...");
		// Parameters:
		//  . queue : the name of the queue
		//  . durable : true if we are declaring a durable queue (the queue will survive a server restart)
		//  . exclusive : true if we are declaring an exclusive queue (restricted to this connection)
		//  . autoDelete : true if we are declaring an autodelete queue (server will delete it when no longer in use)
		//  . arguments : other properties (construction arguments) for the queue
		// Throws: java.io.IOException - if an error is encountered
		// NB : default type is "classic" => use arguments to set another type
		Map<String, Object> arguments;
		
		//--- MAIN QUEUE : CLASSIC with TTL
		arguments = new HashMap<>();
		// Message TTL for "CLASSIC" queue (no TTL for "QUORUM" )
		arguments.put("x-queue-type", "classic"); 
		arguments.put("x-message-ttl", Integer.valueOf(5000)); // TTL = 5 sec 

		arguments.put("x-dead-letter-exchange", Names.EXCHANGE_DLQ_B);
		// Message TTL for "CLASSIC" queue (no TTL for "QUORUM" )
		// "x-dead-letter-routing-key" no set => keep original routing key
		channel.queueDeclare(Names.QUEUE_B, true, false, false, arguments);
		
		//--- DEAD LETTER QUEUE
		arguments = new HashMap<>();
		arguments.put("x-queue-type", "quorum"); 
		channel.queueDeclare(Names.DLQ_B,   true, false, false, arguments);

	    //------------------------------------------------------------------------
	    // BINDING(S)
	    //------------------------------------------------------------------------
	    // Create queue - exchange binding (no error if already exists)
		Tool.log("Declaring bindings(s)...");
		// Parameters :
		// . queue the name of the queue
		// . exchange the name of the exchange
		// . routingKey the routing key to use for the binding
		// Throws: java.io.IOException - if an error is encountered
		channel.queueBind(Names.QUEUE_B, Names.EXCHANGE_QUEUE_B, Names.ROUTING_KEY_B);
		channel.queueBind(Names.DLQ_B,   Names.EXCHANGE_DLQ_B,   Names.ROUTING_KEY_B);

		Tool.close(channel);
	}
}
