package iot.mqtt;

public enum MQTTProtocol {
  MQIsdp("MQIsdp"), MQTT("MQTT");
  public String protocolId;

  MQTTProtocol(String protocolId) {
    this.protocolId = protocolId;
  }

  public static boolean isValid(String protocolId) {
    if (MQTTProtocol.MQIsdp.protocolId.equalsIgnoreCase(protocolId)
        && MQTTProtocol.MQTT.protocolId.equalsIgnoreCase(protocolId)) {
      throw new IllegalArgumentException();
    }
    return true;
  }
}
