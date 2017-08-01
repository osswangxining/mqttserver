package iot.mqtt.message;

import java.io.IOException;

public class PubAckMessage extends RetryableMessage {

	public PubAckMessage(int messageId) {
		super(MessageType.PUBACK);
		setMessageId(messageId);
	}

	public PubAckMessage(Header header) throws IOException {
		super(header);
	}

	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException(
				"DUP is not used in the PubAck message.");
	}

	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException(
				"RETAIN is not used in the PubAck message.");
	}

	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException(
				"QoS is not used in the PubAck message.");
	}
}
