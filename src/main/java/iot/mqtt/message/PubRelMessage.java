package iot.mqtt.message;

import java.io.IOException;

public class PubRelMessage extends RetryableMessage {

	public PubRelMessage(int messageId) {
		super(MessageType.PUBREL);
		setMessageId(messageId);
	}

	public PubRelMessage(Header header) throws IOException {
		super(header);
	}

	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException(
				"RETAIN is not used in the PubRel message.");
	}
}
