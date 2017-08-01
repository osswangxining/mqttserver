package iot.mqtt.processor;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import iot.mqtt.MemPool;
import iot.mqtt.message.ConnAckMessage;
import iot.mqtt.message.ConnAckMessage.ConnectionStatus;
import iot.mqtt.message.ConnectMessage;
import iot.mqtt.message.Message;

public class ConnectProcesser implements Processer {
  private static ConnAckMessage UNACCEPTABLE_PROTOCOL_VERSION = new ConnAckMessage(
      ConnectionStatus.UNACCEPTABLE_PROTOCOL_VERSION);

private static ConnAckMessage ACCEPTED = new ConnAckMessage(
      ConnectionStatus.ACCEPTED);

  @Override
  public Message proc(Message msg, ChannelHandlerContext ctx) {
    ConnectMessage cm = (ConnectMessage) msg;
    if (!"MQIsdp".equalsIgnoreCase(cm.getProtocolId())
            || cm.getProtocolVersion() != 3) {
        return UNACCEPTABLE_PROTOCOL_VERSION;
    }

    int timeout = (int) Math.ceil(cm.getKeepAlive() * 1.5);

    ctx.pipeline().addFirst("readTimeOutHandler",
            new ReadTimeoutHandler(timeout, TimeUnit.SECONDS));

    MemPool.registerClienId(cm.getClientId(), ctx.channel());

    return ACCEPTED;
  }

}