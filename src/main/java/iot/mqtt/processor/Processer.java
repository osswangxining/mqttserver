package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.message.Message;

public interface Processer {

  public Message process(Message msg, ChannelHandlerContext ctx);
}