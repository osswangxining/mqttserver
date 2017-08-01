package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.MemPool;
import iot.mqtt.message.Message;
import iot.mqtt.message.UnsubAckMessage;
import iot.mqtt.message.UnsubscribeMessage;

public class UnsubscribeProcesser implements Processer {

	public Message proc(Message msg, ChannelHandlerContext ctx) {
		if (MemPool.getClientId(ctx.channel()) == null) {
			ctx.channel().close();
			return null;
		}

		UnsubscribeMessage usm = (UnsubscribeMessage) msg;
		for (String topic : usm.getTopics()) {
			MemPool.unregisterTopic(ctx.channel(), topic);
		}
		UnsubAckMessage usam = new UnsubAckMessage();
		usam.setMessageId(usm.getMessageId());

		return usam;
	}
}