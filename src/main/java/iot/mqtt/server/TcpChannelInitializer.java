package iot.mqtt.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import iot.mqtt.handler.MqttMessageHandler;
import iot.mqtt.handler.MqttMessageDecoder;
import iot.mqtt.handler.MqttMessageEncoder;

public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("encoder", new MqttMessageEncoder());
		pipeline.addLast("decoder", new MqttMessageDecoder());
		pipeline.addLast("handler", new MqttMessageHandler());
	}
}