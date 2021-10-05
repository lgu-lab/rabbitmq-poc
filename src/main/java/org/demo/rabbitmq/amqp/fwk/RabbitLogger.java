package org.demo.rabbitmq.amqp.fwk;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Delivery;

public class RabbitLogger {

	private static PrintStream out = null ;
	
	public static void setOutput(PrintStream output) {
		out = output ;
	}

	public static void println(String msg) {
		if ( out != null ) {
			out.println(msg);
		}
	}

	public static void printMessageBody(Delivery message) throws UnsupportedEncodingException {
		printMessageBody(message.getBody());
	}

	public static void printMessageBody(byte[] body) throws UnsupportedEncodingException {
		printMessageBody( new String(body, "UTF-8") );
	}
	public static void printMessageBody(String body) throws UnsupportedEncodingException {
		println(". message body : " + body );
	}

	public static void printMessageProperties(Delivery message) {
		printMessageProperties(message.getProperties());
	}

	public static void printMessageProperties(BasicProperties properties) {
		// Spec AMQP : "Immutable properties of the message. MUST remain unaltered"
		// BasicProperties : getXxx only (no setXxx )
		if (properties != null) {
			println(". message properties : ");
			println(" . Delivery Mode : " + properties.getDeliveryMode()); // Integer
			println(" . Expiration    : " + properties.getExpiration()); // String
			println(" . Priority      : " + properties.getPriority()); // Integer
			println(" . User Id       : " + properties.getUserId()); // String

			println(" . Application Id   : " + properties.getAppId()); // String
			println(" . Content Encoding : " + properties.getContentEncoding()); // String
			println(" . Content Type     : " + properties.getContentType()); // String
			println(" . Correlation Id   : " + properties.getCorrelationId()); // String

			Map<String, Object> headers = properties.getHeaders();
			if (headers != null) {
				println(". message headers : ");
				for (Map.Entry<String, Object> entry : headers.entrySet()) {
					println(" . '" + entry.getKey() + "' : " + entry.getValue());
				}
			} else {
				println(". message headers : no headers (null)");
			}
		} else {
			println(". message properties : no properties (null)");
		}
	}

	public static void printException(Exception exception) {
		println("Exception : " + exception != null ? exception.getClass().getSimpleName() : "null");
	}

}
