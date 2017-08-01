package iot.mqtt.message;

import java.io.IOException;

public class PingRespMessage extends Message {

	public PingRespMessage() {
		super(MessageType.PINGRESP);
	}
	
	public PingRespMessage(Header header) throws IOException{
		super(header);
	}
	
	@Override
	public void setDup(boolean dup) {
		throw new UnsupportedOperationException("DUP is not used in the PINGRESP message.");
	}
	
	@Override
	public void setQos(QoS qos) {
		throw new UnsupportedOperationException("QoS is not used in the PINGRESP message.");
	}
	
	@Override
	public void setRetained(boolean retain) {
		throw new UnsupportedOperationException("RETAIN  is not used in the PINGRESP message.");
	}


}
