package iot.mqtt.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import iot.mqtt.MqttMessageNewDecoder;
import iot.mqtt.MqttMessageNewEncoder;
import iot.mqtt.handler.MqttMessageHandler;

public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("encoder", new MqttMessageNewEncoder());
		pipeline.addLast("decoder", new MqttMessageNewDecoder());
		pipeline.addLast("handler", new MqttMessageHandler());
	}
}