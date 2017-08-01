package iot.mqtt.processor;

import io.netty.channel.ChannelHandlerContext;
import iot.mqtt.message.Message;

public interface Processer {

  public Message proc(Message msg, ChannelHandlerContext ctx);
}