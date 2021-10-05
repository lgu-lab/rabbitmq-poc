package org.demo.rabbitmq.amqp.app5fwk;

import java.io.IOException;

import org.demo.rabbitmq.amqp.commons.Names;
import org.demo.rabbitmq.amqp.commons.Tool;
import org.demo.rabbitmq.amqp.fwk.RabbitConsumer;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Connection;

public class ConsumerQueueC extends RabbitConsumer {

	private static final String QUEUE_NAME = Names.QUEUE_C;
	
	public ConsumerQueueC(Connection connection) throws IOException {
		super(connection, QUEUE_NAME);
	}

//	@Override
//	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
//			throws IOException {
//		String message = new String(body, "UTF-8");
//		Tool.println("Message received (consumer tag = '" + this.getConsumerTag() + "')");
////		Tool.println(" . message body = '" + message + "'");
////		Tool.println(" . consumer tag = '" + this.getConsumerTag() + "'");
//		Tool.printMessageBody(body);
//		Tool.printMessageProperties(properties);
//		
//		Tool.sleep(1000L);
//		if ( message.contains("OOPS") ) {
//			rejectMessage(envelope);
//		}
//		else if ( message.contains("ARGH") ) {
//			requeueMessage(envelope); 
//			// this message will become a "poison message"
//			// message headers : 'x-delivery-count' : 2, 3 ,4, etc
//			// Quorum queues keep track of the number of unsuccessful delivery attempts 
//			// and expose it in the "x-delivery-count" header that is included with any redelivered message.
//		}
//		else {
//			ackMessage(envelope);
//		}
//	}
	
	protected void processMessage(String messageBody, BasicProperties messageProperties) {
		Tool.sleep(1000L);
		if ( messageBody.contains("OOPS") ) {
			rejectMessage();
		}
		else if ( messageBody.contains("ARGH") ) {
			requeueMessage(); 
			// this message will become a "poison message"
			// message headers : 'x-delivery-count' : 2, 3 ,4, etc
			// Quorum queues keep track of the number of unsuccessful delivery attempts 
			// and expose it in the "x-delivery-count" header that is included with any redelivered message.
		}
		else {
			// Normal processing => ACK MESSAGE
		}
	}
}
