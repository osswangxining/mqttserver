package iot.mqtt.message;

public class Header {
  MessageType type;
  boolean dup;
  QoS qos = QoS.AT_MOST_ONCE;
  boolean retain;

  public Header(MessageType type, boolean dup, QoS qos, boolean retain) {
    this.type = type;
    this.dup = dup;
    this.qos = qos;
    this.retain = retain;
  }

  public Header(byte flags) {
    retain = (flags & 1) > 0;
    qos = QoS.valueOf((flags & 0x6) >> 1);
    dup = (flags & 8) > 0;
    type = MessageType.valueOf((flags >> 4) & 0xF);
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public boolean isDup() {
    return dup;
  }

  public void setDup(boolean dup) {
    this.dup = dup;
  }

  public QoS getQos() {
    return qos;
  }

  public void setQos(QoS qos) {
    this.qos = qos;
  }

  public boolean isRetain() {
    return retain;
  }

  public void setRetain(boolean retain) {
    this.retain = retain;
  }

  byte encode() {
    byte b = 0;
    b = (byte) (type.getValue() << 4);
    b |= retain ? 1 : 0;
    b |= qos.getValue() << 1;
    b |= dup ? 8 : 0;
    return b;
  }

  @Override
  public String toString() {
    return "Header [type=" + type + ", dup=" + dup + ", qos=" + qos + ", retain=" + retain + "]";
  }
}