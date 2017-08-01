package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.MemPool;
import iot.mqtt.handler.entity.ChannelEntity;
import iot.mqtt.handler.entity.TcpChannelEntity;
import iot.mqtt.message.DisconnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.message.QoS;
import iot.mqtt.message.SubAckMessage;
import iot.mqtt.message.SubscribeMessage;

public class SubscribeProcesser implements Processer {

	private static DisconnectMessage DISCONNECT = new DisconnectMessage();

	public Message proc(Message msg, ChannelHandlerContext ctx) {
		String clientId = MemPool.getClientId(ctx.channel());
		if (clientId == null) {
			return DISCONNECT;
		}

		SubscribeMessage sm = (SubscribeMessage) msg;
		SubAckMessage sam = new SubAckMessage();
		sam.setMessageId(sm.getMessageId());
		if (sm.getTopics() != null) {
			for (String topic : sm.getTopics()) {
				sam.addQoS(QoS.AT_MOST_ONCE);
				ChannelEntity channelEntity = new TcpChannelEntity(
						ctx.channel());
				MemPool.registerTopic(channelEntity, topic);
			}
		}

		return sam;
	}
}