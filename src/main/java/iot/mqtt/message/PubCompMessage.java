package iot.mqtt.message;

import java.io.IOException;

public class PubCompMessage extends RetryableMessage {

	public PubCompMessage(int messageId) {
		super(MessageType.PUBCOMP);
		setMessageId(messageId);
	}

	public PubCompMessage(Header header) throws IOException {
		super(header);
	}

	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException(
				"DUP is not used in the PubComp message.");
	}

	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException(
				"RETAIN is not used in the PubComp message.");
	}

	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException(
				"QoS is not used in the PubComp message.");
	}
}
