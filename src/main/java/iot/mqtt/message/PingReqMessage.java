package iot.mqtt.message;

import java.io.IOException;

public class PingReqMessage extends Message {

	public PingReqMessage() {
		super(MessageType.PINGREQ);
	}
	
	public PingReqMessage(Header header) throws IOException{
		super(header);
	}
	
	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException("DUP is not used in the PINGREQ message.");
	}
	
	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException("QoS is not used in the PINGREQ message.");
	}
	
	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException("RETAIN  is not used in the PINGREQ message.");
	}


}
