package org.demo.rabbitmq.amqp.fwk;

public class RejectMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RejectMessageException() {
		super();
		RabbitLogger.printException(this);
	}

}
