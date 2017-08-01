package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.message.DisconnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.message.PingRespMessage;
import iot.mqtt.meta.MemoryMetaPool;

public class PingReqProcesser implements Processer {

  @Override
  public Message process(Message msg, ChannelHandlerContext ctx) {
    if (MemoryMetaPool.getClientId(ctx.channel()) == null) {
      return new DisconnectMessage();
    }

    return new PingRespMessage();
  }

}
