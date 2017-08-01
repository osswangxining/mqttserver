package iot.mqtt.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Message {

  private static char nextId = 1;
  private final Header header;

  public Message(MessageType type) {
    header = new Header(type, false, QoS.AT_MOST_ONCE, false);
  }

  public Message(Header header) throws IOException {
    this.header = header;
  }

  private int readMsgLength(InputStream in) throws IOException {
    int msgLength = 0;
    int multiplier = 1;
    int digit;
    do {
      digit = in.read();
      msgLength += (digit & 0x7f) * multiplier;
      multiplier *= 128;
    } while ((digit & 0x80) > 0);
    return msgLength;
  }

  protected int messageLength() {
    return 0;
  }

  private void writeMsgLength(OutputStream out) throws IOException {
    int msgLength = messageLength();
    int val = msgLength;
    do {
      byte b = (byte) (val & 0x7F);
      val >>= 7;
      if (val > 0) {
        b |= 0x80;
      }
      out.write(b);
    } while (val > 0);
  }

  final void read(InputStream in) throws IOException {
    int msgLength = readMsgLength(in);
    readMessage(in, msgLength);
  }

  protected void readMessage(InputStream in, int msgLength) throws IOException {

  }

  public final byte[] toBytes() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      write(baos);
    } catch (IOException e) {
    }
    return baos.toByteArray();
  }

  public final void write(OutputStream out) throws IOException {
    out.write(header.encode());
    writeMsgLength(out);
    writeMessage(out);
  }

  protected void writeMessage(OutputStream out) throws IOException {
  }

  public void setRetained(boolean retain) {
    header.retain = retain;
  }

  public boolean isRetained() {
    return header.retain;
  }

  public void setQos(QoS qos) {
    header.qos = qos;
  }

  public QoS getQos() {
    return header.qos;
  }

  public void setDup(boolean dup) {
    header.dup = dup;
  }

  public boolean isDup() {
    return header.dup;
  }

  public MessageType getType() {
    return header.type;
  }

  public static char nextId() {
    return nextId++;
  }
}
