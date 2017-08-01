package iot.mqtt.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnAckMessage extends Message {

	public final static int MESSAGE_LENGTH = 2;

	public enum ConnectionStatus {
		ACCEPTED,

		UNACCEPTABLE_PROTOCOL_VERSION,

		IDENTIFIER_REJECTED,

		SERVER_UNAVAILABLE,

		BAD_USERNAME_OR_PASSWORD,

		NOT_AUTHORIZED
	}

	private ConnectionStatus status;

	public ConnAckMessage() {
		super(MessageType.CONNACK);
	}

	public ConnAckMessage(Header header) throws IOException {
		super(header);
	}

	public ConnAckMessage(ConnectionStatus status) {
		super(MessageType.CONNACK);
		if (status == null) {
			throw new IllegalArgumentException(
					"The status of ConnAck Message can't be null");
		}
		this.status=status;
	}

	@Override
	protected int messageLength() {
		return MESSAGE_LENGTH;
	}

	@Override
	protected void readMessage(InputStream in, int msgLength)
			throws IOException {
		if (msgLength != MESSAGE_LENGTH) {
			throw new IllegalStateException(
					"Message Length must be 2 for CONNACK. Current value: "
							+ msgLength);
		}
		// Ignore first byte
		in.read();
		int result = in.read();
		switch (result) {
		case 0:
			status = ConnectionStatus.ACCEPTED;
			break;
		case 1:
			status = ConnectionStatus.UNACCEPTABLE_PROTOCOL_VERSION;
			break;
		case 2:
			status = ConnectionStatus.IDENTIFIER_REJECTED;
			break;
		case 3:
			status = ConnectionStatus.SERVER_UNAVAILABLE;
			break;
		case 4:
			status = ConnectionStatus.BAD_USERNAME_OR_PASSWORD;
			break;
		case 5:
			status = ConnectionStatus.NOT_AUTHORIZED;
			break;
		default:
			throw new UnsupportedOperationException(
					"Unsupported CONNACK code: " + result);
		}
	}

	@Override
	protected void writeMessage(OutputStream out) throws IOException {
		out.write(0x00);
		switch (status) {
		case ACCEPTED:
			out.write(0x00);
			break;
		case UNACCEPTABLE_PROTOCOL_VERSION:
			out.write(0x01);
			break;
		case IDENTIFIER_REJECTED:
			out.write(0x02);
			break;
		case SERVER_UNAVAILABLE:
			out.write(0x03);
			break;
		case BAD_USERNAME_OR_PASSWORD:
			out.write(0x04);
			break;
		case NOT_AUTHORIZED:
			out.write(0x05);
			break;
		default:
			throw new UnsupportedOperationException(
					"Unsupported CONNACK code: " + status);
		}
	}

	public ConnectionStatus getStatus() {
		return status;
	}

	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException(
				"DUP is not used in the CONNACK message.");
	}

	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException(
				"RETAIN is not used in the CONNACK message.");
	}

	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException(
				"QoS is not used in the CONNACK message.");
	}

}
