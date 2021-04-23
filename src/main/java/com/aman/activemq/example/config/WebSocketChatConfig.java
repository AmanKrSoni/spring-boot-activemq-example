package com.aman.activemq.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketChatConfig implements WebSocketMessageBrokerConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketChatConfig.class);

	@Value("${spring.activemq.host}")
	private String host;

	@Value("${service.engine.activemq.relayport}")
	private int port;

	@Value("${active-mq.user}")
	private String username;

	@Value("${active-mq.password}")
	private String password;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocketApp").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/websocketApp").setAllowedOrigins("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {

		registry.setApplicationDestinationPrefixes("/app");
		registry.enableStompBrokerRelay("/topic", "/queue/")
				.setRelayHost(host)
				.setRelayPort(port)
				.setSystemLogin(username)
				.setSystemPasscode(password)
				.setClientLogin(username)
				.setClientPasscode(password)
				.setTcpClient(createTcpClient());


	}

	// for aws activemq stomp endpoint we have to added this else not working
	private SocketAddress getAddress() {
		try {
			InetAddress addr = InetAddress.getByName(host);
			SocketAddress sockaddr = new InetSocketAddress(addr, port);
			return sockaddr;
		} catch (UnknownHostException e) {
			logger.error("failed to connect");
			e.printStackTrace();
		}
		return null;
	}

	private ReactorNettyTcpClient<byte[]> createTcpClient() {
		ReactorNettyTcpClient unSecured;
		unSecured = new ReactorNettyTcpClient<>(
				client -> client.connectAddress(() -> getAddress()).sslSupport(),
				new StompReactorNettyCodec());
		return unSecured;
	}

}
