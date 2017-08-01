package iot.mqtt.message;

public enum MessageType {
  CONNECT(1), CONNACK(2), PUBLISH(3), PUBACK(4), PUBREC(5), PUBREL(6), PUBCOMP(7), SUBSCRIBE(8), SUBACK(9), UNSUBSCRIBE(
      10), UNSUBACK(11), PINGREQ(12), PINGRESP(13), DISCONNECT(14);

  final private int value;

  MessageType(int value) {
    this.value = value;
  }

  static MessageType valueOf(int i) {
    for (MessageType t : MessageType.values()) {
      if (t.value == i)
        return t;
    }
    return null;
  }

  public int getValue() {
    return value;
  }
}
