package com.study.loge.betchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Web Socket handshake를 위한 주소입닏.
        registry.addEndpoint("/ws");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // chatting을 전달하기 위한 url은 /pub로 시작합니다.
        registry.setApplicationDestinationPrefixes("/pub");

        // chatting을 전달받기 위해 클라이언트는 /sub로 시작하는 url을 subscribe해야합니다.
        registry.enableSimpleBroker("/sub");
    }
}
