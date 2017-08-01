package iot.mqtt.handler.entity;

import io.netty.channel.Channel;

public class WebSocketChannelEntity extends TcpChannelEntity {

	public WebSocketChannelEntity(Channel channel) {
		super(channel);
	}
}