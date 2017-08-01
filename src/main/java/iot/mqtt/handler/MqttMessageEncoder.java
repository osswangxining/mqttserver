package iot.mqtt.handler;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import iot.mqtt.message.Message;

@Sharable
public class MqttMessageEncoder extends MessageToMessageEncoder<Object> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg,
			List<Object> out) throws Exception {
		if (!(msg instanceof Message)) {
			return;
		}
		
		byte[] data = ((Message) msg).toBytes();

		out.add(Unpooled.wrappedBuffer(data));
	}
}