package org.demo.rabbitmq.amqp;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class AppInitRabbitMQ {

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
	    channel.exchangeDeclare(Names.EXCHANGE_DIRECT1, BuiltinExchangeType.DIRECT, true);
	     
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
		Map<String, Object> queueArguments = new HashMap<>();
		queueArguments.put("x-queue-type", "quorum"); 
		channel.queueDeclare(Names.QUEUE1, true, false, false, queueArguments);
		channel.queueDeclare(Names.QUEUE2, true, false, false, queueArguments);
		channel.queueDeclare(Names.QUEUE3, true, false, false, queueArguments);
	    // If already exists with another type 
	    // ERROR : :inequivalent arg 'x-queue-type' for queue 'queue-test3' in vhost '/':
	    // received the value 'quorum' of type 'longstr' but current is none
		//Tool.pause("Queues created.");

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
		channel.queueBind(Names.QUEUE1, Names.EXCHANGE_DIRECT1, Names.ROUTING_KEY1);
		channel.queueBind(Names.QUEUE2, Names.EXCHANGE_DIRECT1, Names.ROUTING_KEY2);

		Map<String, Object> bindingArguments = new HashMap<>();
		channel.queueBind(Names.QUEUE3, Names.EXCHANGE_DIRECT1, Names.ROUTING_KEY1, bindingArguments);
		channel.queueBind(Names.QUEUE3, Names.EXCHANGE_DIRECT1, Names.ROUTING_KEY2, bindingArguments);
		//Tool.pause("Binding created.");
		
		Tool.close(channel);
	}
}
