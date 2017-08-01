package iot.mqtt.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import iot.mqtt.message.ConnAckMessage;
import iot.mqtt.message.ConnAckMessage.ConnectionStatus;
import iot.mqtt.message.DisconnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.message.MessageType;
import iot.mqtt.message.PingRespMessage;
import iot.mqtt.processor.ConnectProcesser;
import iot.mqtt.processor.DisConnectProcesser;
import iot.mqtt.processor.PingReqProcesser;
import iot.mqtt.processor.Processer;
import iot.mqtt.processor.PublishProcesser;
import iot.mqtt.processor.SubscribeProcesser;
import iot.mqtt.processor.UnsubscribeProcesser;

public class MqttMessageHandler extends ChannelInboundHandlerAdapter {
	private static PingRespMessage PINGRESP = new PingRespMessage();

	private static final Map<MessageType, Processer> processers;
	static {
		Map<MessageType, Processer> map = new HashMap<MessageType, Processer>(
				6);

		map.put(MessageType.CONNECT, new ConnectProcesser());
		map.put(MessageType.PUBLISH, new PublishProcesser());
		map.put(MessageType.SUBSCRIBE, new SubscribeProcesser());
		map.put(MessageType.UNSUBSCRIBE, new UnsubscribeProcesser());
		map.put(MessageType.PINGREQ, new PingReqProcesser());
		map.put(MessageType.DISCONNECT, new DisConnectProcesser());

		processers = Collections.unmodifiableMap(map);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
			throws Exception {
		try {
			if (e.getCause() instanceof ReadTimeoutException) {
				ctx.write(PINGRESP).addListener(
						ChannelFutureListener.CLOSE_ON_FAILURE);
			} else {
				ctx.channel().close();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			ctx.channel().close();
		}

		e.printStackTrace();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		Message msg = (Message) obj;
		Processer p = processers.get(msg.getType());
		if (p == null) {
			return;
		}
		Message rmsg = p.process(msg, ctx);
		if (rmsg == null) {
			return;
		}

		if (rmsg instanceof ConnAckMessage
				&& ((ConnAckMessage) rmsg).getStatus() != ConnectionStatus.ACCEPTED) {
			ctx.write(rmsg).addListener(ChannelFutureListener.CLOSE);
		} else if (rmsg instanceof DisconnectMessage) {
			ctx.write(rmsg).addListener(ChannelFutureListener.CLOSE);
		} else {
			ctx.write(rmsg).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}