package iot.mqtt;

public enum MQTTVersion {
  MQTT_VERSION_3_1(3), MQTT_VERSION_3_1_1(4);

  public int version;

  MQTTVersion(int version) {
    this.version = version;
  }

  public static boolean isValid(byte version) {
    if (version != MQTTVersion.MQTT_VERSION_3_1.version && version != MQTTVersion.MQTT_VERSION_3_1_1.version) {
      throw new IllegalArgumentException();
    }
    return true;
  }
}
