package iot.mqtt.processor;

import java.util.Set;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import iot.mqtt.MemPool;
import iot.mqtt.handler.entity.ChannelEntity;
import iot.mqtt.message.DisconnectMessage;
import iot.mqtt.message.Message;
import iot.mqtt.message.PublishMessage;

public class PublishProcesser implements Processer {
  private static final InternalLogger logger = InternalLoggerFactory.getInstance(PublishProcesser.class);
  private static DisconnectMessage DISCONNECT = new DisconnectMessage();

  @Override
  public Message proc(Message msg, ChannelHandlerContext ctx) {
    String clientId = MemPool.getClientId(ctx.channel());
    if (clientId == null) {
      return DISCONNECT;
    }

    PublishMessage pm = (PublishMessage) msg;
    Set<ChannelEntity> channelEntitys = MemPool.getChannelByTopics(pm.getTopic());
    if (channelEntitys == null) {
      return null;
    }

    for (ChannelEntity channelEntity : channelEntitys) {
      logger.info("PUBLISH to ChannelEntity topic = " + pm.getTopic() + " payload = " + pm.getDataAsString());
      channelEntity.write(pm);
    }

    return null;
  }

}
