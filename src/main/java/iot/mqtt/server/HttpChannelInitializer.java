package iot.mqtt.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import iot.mqtt.MqttMessageWebSocketFrameDecoder;
import iot.mqtt.MqttMessageWebSocketFrameEncoder;
import iot.mqtt.handler.HttpRequestHandler;
import iot.mqtt.handler.MqttMessageHandler;
import iot.mqtt.handler.http.HttpJsonpTransport;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
	private final static String websocketUri = "/websocket";

	private HttpRequestHandler httpRequestHandler = new HttpRequestHandler(
			websocketUri);

	static {
		HttpJsonpTransport httpJsonpTransport = new HttpJsonpTransport();
		HttpRequestHandler.registerTransport(httpJsonpTransport);
	}

	@Override
	public void initChannel(final SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new HttpServerCodec(),
				new MqttMessageWebSocketFrameEncoder(),
				new HttpObjectAggregator(65536), httpRequestHandler,
				new WebSocketServerProtocolHandler(websocketUri),
				new MqttMessageWebSocketFrameDecoder(),
				new MqttMessageHandler());
	}
}