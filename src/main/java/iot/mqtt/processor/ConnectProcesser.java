package iot.mqtt.processor;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import iot.mqtt.MQTTProtocol;
import iot.mqtt.MQTTVersion;
import iot.mqtt.message.ConnAckMessage;
import iot.mqtt.message.ConnAckMessage.ConnectionStatus;
import iot.mqtt.message.ConnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.meta.MemoryMetaPool;

public class ConnectProcesser implements Processer {
  private static ConnAckMessage UNACCEPTABLE_PROTOCOL_VERSION = new ConnAckMessage(
      ConnectionStatus.UNACCEPTABLE_PROTOCOL_VERSION);

  private static ConnAckMessage ACCEPTED = new ConnAckMessage(ConnectionStatus.ACCEPTED);

  @Override
  public Message process(Message msg, ChannelHandlerContext ctx) {
    ConnectMessage cm = (ConnectMessage) msg;
    if (!MQTTProtocol.isValid(cm.getProtocolId()) || !MQTTVersion.isValid(cm.getProtocolVersion())) {
      return UNACCEPTABLE_PROTOCOL_VERSION;
    }

    int timeout = (int) Math.ceil(cm.getKeepAlive() * 1.5);

    ctx.pipeline().addFirst("readTimeOutHandler", new ReadTimeoutHandler(timeout, TimeUnit.SECONDS));

    MemoryMetaPool.registerClienId(cm.getClientId(), ctx.channel());

    return ACCEPTED;
  }

}
