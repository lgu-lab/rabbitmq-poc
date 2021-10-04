package org.demo.rabbitmq.amqp.commons;

public class Names {

	//-----------------------------------------------------------------
	// App1
	//-----------------------------------------------------------------
	public static final String EXCHANGE_DIRECT1 = "ex-direct-1" ;

	public static final String QUEUE1 = "q-test1" ; // "ex-direct-1" with "routing-key-1"
	public static final String QUEUE2 = "q-test2" ; // "ex-direct-1" with "routing-key-2"
	public static final String QUEUE3 = "q-test3" ; // "ex-direct-1" with "routing-key-1" + "routing-key-2"
	
	public static final String ROUTING_KEY1 = "routing-key-1" ;
	public static final String ROUTING_KEY2 = "routing-key-2" ;
	

	//-----------------------------------------------------------------
	// App2
	//-----------------------------------------------------------------
	public static final String EXCHANGE_QUEUE_A = "ex-direct-a" ;
	public static final String QUEUE_A = "queue-a" ;

	public static final String EXCHANGE_DLQ_A = "ex-dlq-a" ;
	public static final String DLQ_A = "dlq-a" ;
	
	public static final String ROUTING_KEY_A = "routing-key-a" ;

	//-----------------------------------------------------------------
	// App3
	//-----------------------------------------------------------------
	public static final String EXCHANGE_QUEUE_B = "ex-direct-b" ;
	public static final String QUEUE_B = "queue-b" ;

	public static final String EXCHANGE_DLQ_B = "ex-dlq-b" ;
	public static final String DLQ_B = "dlq-b" ;
	
	public static final String ROUTING_KEY_B = "routing-key-b" ;

}
