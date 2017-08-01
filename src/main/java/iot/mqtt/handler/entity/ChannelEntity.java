package iot.mqtt.handler.entity;

import io.netty.channel.Channel;
import iot.mqtt.message.Message;

public abstract class ChannelEntity {

	public Channel getChannel() {
		return null;
	}

	public abstract void write(Message message);

	@Override
	public int hashCode() {
		return getChannel().hashCode();
	}

	@Override
	public String toString() {
		return getChannel().toString();
	}
}