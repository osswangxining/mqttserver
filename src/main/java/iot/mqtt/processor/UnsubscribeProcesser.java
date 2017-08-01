package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.message.Message;
import iot.mqtt.message.UnsubAckMessage;
import iot.mqtt.message.UnsubscribeMessage;
import iot.mqtt.meta.MemoryMetaPool;

public class UnsubscribeProcesser implements Processer {

	public Message process(Message msg, ChannelHandlerContext ctx) {
		if (MemoryMetaPool.getClientId(ctx.channel()) == null) {
			ctx.channel().close();
			return null;
		}

		UnsubscribeMessage usm = (UnsubscribeMessage) msg;
		for (String topic : usm.getTopics()) {
			MemoryMetaPool.unregisterTopic(ctx.channel(), topic);
		}
		UnsubAckMessage usam = new UnsubAckMessage();
		usam.setMessageId(usm.getMessageId());

		return usam;
	}
}