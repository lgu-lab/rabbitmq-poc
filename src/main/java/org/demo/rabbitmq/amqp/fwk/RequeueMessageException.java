package org.demo.rabbitmq.amqp.fwk;

public class RequeueMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequeueMessageException() {
		super();
		RabbitLogger.printException(this);
	}

}
