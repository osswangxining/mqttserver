package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.MemPool;
import iot.mqtt.message.DisconnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.message.PingRespMessage;

public class PingReqProcesser implements Processer {

  @Override
  public Message proc(Message msg, ChannelHandlerContext ctx) {
    if (MemPool.getClientId(ctx.channel()) == null) {
      return new DisconnectMessage();
    }

    return new PingRespMessage();
  }

}
