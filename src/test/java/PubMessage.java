
import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class PubMessage {
  public static void main(String[] args)  {

    String tcpUrl = "tcp://127.0.0.1:1883";
    String clientId = "d:client001";
    String topicName = "v1/devices/me/telemetry";

    System.out.println("start...");
    try {
      pubMsg(tcpUrl, clientId, topicName);
    } catch (UnsupportedEncodingException | MqttException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("PUB Done!");
  }

  public static void pubMsg(String tcpUrl, String clientId, String topicName)
      throws MqttException, UnsupportedEncodingException {
    MqttClient client = new MqttClient(tcpUrl, clientId);
    MqttConnectOptions mqcConf = new MqttConnectOptions();
    mqcConf.setConnectionTimeout(300);
    mqcConf.setKeepAliveInterval(1200);
    client.connect(mqcConf);

    MqttTopic topic = client.getTopic(topicName);
    for (int i = 0; i < 10; i++) {
      String message = "{\"id\":" + (i+1) + ",\"temp\":12}";
      topic.publish(message.getBytes("utf8"), 1, false);
    }
    client.disconnect();
  }
}