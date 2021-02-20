package com.study.loge.betchat.component;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// 클라이언트의 request가 Controller 또는 Broker에 도달하기 전, 필요한 처리를 수행하는 클래스입니다.
@Component
public class StompInboundChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 유효하지 않은 room key subscribe를 제한합니다.
        return message;
    }
}
