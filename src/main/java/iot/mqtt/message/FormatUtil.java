package iot.mqtt.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FormatUtil {

  public static String dumpByteArray(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      byte b = bytes[i];
      int iVal = b & 0xFF;
      int byteN = Integer.parseInt(Integer.toBinaryString(iVal));
      sb.append(String.format("%1$02d: %2$08d %3$1c %3$d\n", i, byteN, iVal));
    }
    return sb.toString();
  }

  public static byte[] toMQttString(String s) {
    if (s == null) {
      return new byte[0];
    }
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(byteOut);
    try {
      dos.writeUTF(s);
      dos.flush();
    } catch (IOException e) {
      // SHould never happen;
      return new byte[0];
    }
    return byteOut.toByteArray();
  }

  public static String toString(byte[] data) {
    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    DataInputStream dis = new DataInputStream(bais);
    try {
      return dis.readUTF();
    } catch (IOException e) {
    }
    return null;
  }
}
