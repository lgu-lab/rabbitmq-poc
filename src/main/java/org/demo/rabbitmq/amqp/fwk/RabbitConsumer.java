package org.demo.rabbitmq.amqp.fwk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public abstract class RabbitConsumer extends DefaultConsumer {

	private final String queue;
	
	public RabbitConsumer(Connection connection, String queue) throws IOException {
		super(connection.createChannel());
		this.queue = queue;
	}

	public void consume() throws IOException {
		Channel channel = super.getChannel();
		channel.basicConsume(queue, this);
	}
	
	/**
	 * Message processing method
	 * @param messageBody
	 * @param messageProperties
	 */
	protected abstract void processMessage(String messageBody, BasicProperties messageProperties) ;
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties messageProperties, byte[] body)
			throws IOException {
		RabbitLogger.println("Message received (consumer tag = '" + this.getConsumerTag() + "')");
		String messageBody = new String(body, "UTF-8");
		RabbitLogger.printMessageBody(messageBody);
		RabbitLogger.printMessageProperties(messageProperties);
		
		try {
			// Message processing
			processMessage(messageBody, messageProperties) ;
			// Normal end of message processing => ACK
			ackMessage(envelope);
		} catch (RejectMessageException e) {
			rejectMessage(envelope);
		} catch (RequeueMessageException e) {
			requeueMessage(envelope); 
		} 
	}
	
	protected String messageAsString(byte[] messageBody) throws IOException {
		try {
			return new String(messageBody, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	protected void rejectMessage() {
		throw new RejectMessageException();
	}
	
	protected void requeueMessage() {
		throw new RequeueMessageException();
	}
	
	/**
	 * Acknowledge message 
	 * @param envelope
	 * @throws IOException
	 */
	private void ackMessage(Envelope envelope) throws IOException {
		RabbitLogger.println("[ ACK MESSAGE ]");
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
	private void rejectMessage(Envelope envelope) throws IOException {
		RabbitLogger.println("[ REJECT MESSAGE ]");
		Channel channel = getChannel();
		// basicReject
		// Reject a message
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} 
		//     or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . requeue : true if the rejected message should be requeued rather than discarded/dead-lettered
		channel.basicReject(envelope.getDeliveryTag(), false);
	}

	private void requeueMessage(Envelope envelope) throws IOException {
		RabbitLogger.println("[ REQUEUE MESSAGE ]");
		Channel channel = getChannel();
		// basicReject
		// Reject a message
		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} 
		//     or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
	    // . requeue : true if the rejected message should be requeued rather than discarded/dead-lettered
		channel.basicReject(envelope.getDeliveryTag(), true);
	}

//	private void nackMessage(Envelope envelope) throws IOException {
//		Channel channel = getChannel();
//		// basicNack (like basic reject but with 'multiple' parameter)
//		// Reject one or several received messages.
//		// . deliveryTag : the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
//	    // . multiple : true to reject all messages up to and including the supplied delivery tag; false to reject just the supplied delivery tag.
//	    // . requeue : true if the rejected message(s) should be requeued rather than discarded/dead-lettered
//		channel.basicNack(envelope.getDeliveryTag(), false, false);
//	}
}
