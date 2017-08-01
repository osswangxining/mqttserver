package iot.mqtt.message;

import java.io.IOException;

public class UnsubAckMessage extends RetryableMessage {

	public UnsubAckMessage(Header header) throws IOException {
		super(header);
	}
	
	public UnsubAckMessage(){
		super(MessageType.UNSUBACK);
	}
	
	 @Override
	  public void setDup(boolean dup) {
	    throw new UnsupportedOperationException("DUP is not used in the UnsubAck message.");
	  }

	  @Override
	  public void setRetained(boolean retain) {
	    throw new UnsupportedOperationException("RETAIN is not used in the UnsubAck message.");
	  }

	  @Override
	  public void setQos(QoS qos) {
	    throw new UnsupportedOperationException("QoS is not used in the UnsubAck message.");
	  }

}
