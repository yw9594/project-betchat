package com.study.loge.betchat.component;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// 클라이언트에서 subscribe 요청 시 방에 참가 상태로 만들기 위한 클래스입니다.
@Component
public class StompInboundChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        return message;
    }
}
