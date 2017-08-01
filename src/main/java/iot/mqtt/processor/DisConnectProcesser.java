package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.message.Message;

public class DisConnectProcesser implements Processer {

  public Message process(Message msg, ChannelHandlerContext ctx) {
    ctx.channel().close();
    return null;
  }
}