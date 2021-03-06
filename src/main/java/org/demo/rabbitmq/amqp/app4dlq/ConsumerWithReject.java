package org.demo.rabbitmq.amqp.app4dlq;

import java.io.IOException;

import org.demo.rabbitmq.amqp.commons.Tool;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerWithReject extends DefaultConsumer {

	public ConsumerWithReject(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		String message = new String(body, "UTF-8");
		Tool.println("Message received (consumer tag = '" + this.getConsumerTag() + "')");
//		Tool.println(" . message body = '" + message + "'");
//		Tool.println(" . consumer tag = '" + this.getConsumerTag() + "'");
		Tool.printMessageBody(body);
		Tool.printMessageProperties(properties);
		
		Tool.sleep(1000L);
		if ( message.contains("OOPS") ) {
			rejectMessage(envelope);
		}
		else if ( message.contains("ARGH") ) {
			requeueMessage(envelope); 
			// this message will become a "poison message"
			// message headers : 'x-delivery-count' : 2, 3 ,4, etc
			// Quorum queues keep track of the number of unsuccessful delivery attempts 
			// and expose it in the "x-delivery-count" header that is included with any redelivered message.
		}
		else {
			ackMessage(envelope);
		}
	}

	/**
	 * Acknowledge message 
	 * @param envelope
	 * @throws IOException
	 */
	protected void ackMessage(Envelope envelope) throws IOException {
		Channel channel = getChannel();
		// basicAck
		// Acknowledge one or several received messages
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} 
		//      or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . multiple : true to acknowledge all messages up to and
		channel.basicAck(envelope.getDeliveryTag(), false);
	}

	/**
	 * Reject a message (not "requeued", goes to DLQ if any)
	 * @param envelope
	 * @throws IOException
	 */
	protected void rejectMessage(Envelope envelope) throws IOException {
		Channel channel = getChannel();
		// basicReject
		// Reject a message
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} 
		//     or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . requeue : true if the rejected message should be requeued rather than discarded/dead-lettered
		channel.basicReject(envelope.getDeliveryTag(), false);
	}

	protected void requeueMessage(Envelope envelope) throws IOException {
		Channel channel = getChannel();
		// basicReject
		// Reject a message
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} 
		//     or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . requeue : true if the rejected message should be requeued rather than discarded/dead-lettered
		channel.basicReject(envelope.getDeliveryTag(), true);
	}

	protected void nackMessage(Envelope envelope) throws IOException {
		Channel channel = getChannel();
		// basicNack
		// Reject one or several received messages.
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . multiple : true to reject all messages up to and including the supplied delivery tag; false to reject just the supplied delivery tag.
	    // . requeue : true if the rejected message(s) should be requeued rather than discarded/dead-lettered
		channel.basicNack(envelope.getDeliveryTag(), false, false);
	}
}
