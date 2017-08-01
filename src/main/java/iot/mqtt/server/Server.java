package iot.mqtt.server;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class Server {
  private static final InternalLogger logger = InternalLoggerFactory.getInstance(Server.class);

  private final int port = 1883;
  private final int httpPort = 8888;

  private List<Channel> channels = new ArrayList<Channel>();
  private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
  private EventLoopGroup workerGroup = new NioEventLoopGroup();

  public Server() {
  }

  private ServerBootstrap getDefaultServerBootstrap() {
    ServerBootstrap server = new ServerBootstrap();
    server.group(bossGroup, workerGroup).option(ChannelOption.SO_BACKLOG, 1000).option(ChannelOption.TCP_NODELAY, true)
        .channel(NioServerSocketChannel.class).childOption(ChannelOption.SO_KEEPALIVE, true);
    return server;
  }

  public ChannelFuture run() throws Exception {
    InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);

    Channel channle = getDefaultServerBootstrap().childHandler(new TcpChannelInitializer()).bind(port).sync().channel();
    channels.add(channle);

    logger.info("MQTT server is started at port " + port + '.');

    ChannelFuture future = getDefaultServerBootstrap().childHandler(new HttpChannelInitializer()).bind(httpPort);

    Channel httpChannel = future.sync().channel();
    channels.add(httpChannel);

    logger.info("MQTT websocket server is started at port " + httpPort + '.');

    return future;
  }

  public void destroy() {
    logger.info("MQTT server is stopping...");
    for (Channel channel : channels) {
      channel.close();
    }
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
    logger.info("MQTT server is stopped...");
  }

  public static void main(String[] args) throws Exception {
    logger.info("starting mqtt server ...");
    final Server server = new Server();
    ChannelFuture future = server.run();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        server.destroy();
      }
    });

    future.channel().closeFuture().syncUninterruptibly();
  }
}