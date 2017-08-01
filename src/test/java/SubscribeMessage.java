

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscribeMessage implements MqttCallback {

	private MqttClient client;

	public SubscribeMessage() {
	}

	public static void main(String[] args) {
		String tcpUrl = "tcp://127.0.0.1:1883";
		String clientId = "subscriber-client001";
//		String topicName = "sub/client1";
		String topicName = "v1/devices/me/telemetry";

		new SubscribeMessage().doDemo(tcpUrl, clientId, topicName);
	}

	public void doDemo(String tcpUrl, String clientId, String topicName) {
		try {
		  MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		  mqttConnectOptions.setMqttVersion(4);
			client = new MqttClient(tcpUrl, clientId);
			client.connect(mqttConnectOptions);
			client.setCallback(this);
			client.subscribe(topicName);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		System.out.println("[GOT PUBLISH MESSAGE] : " + message);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
}