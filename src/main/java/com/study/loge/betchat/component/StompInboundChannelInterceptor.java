package com.study.loge.betchat.component;

import com.study.loge.betchat.service.UserParticipateExitService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// StompInboudChannel의 Interceptor 클래스입니다.
@AllArgsConstructor
@Component
public class StompInboundChannelInterceptor implements ChannelInterceptor {
    private UserParticipateExitService userParticipateExitService;

    // Message를 Controller 또는 Broker로 보내기 전, 전처리를 수행합니다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);
        SimpMessageType messageType = stompHeaderAccessor.getMessageType();

        try {
            // message type에 따라 특정 로직을 수행합니다.
            switch (messageType) {
                // subscribe 요청 시 Participate table에 등록합니다.
                case SUBSCRIBE:
                    userParticipateExitService.processParticipate(message);
                    break;
                case DISCONNECT:
                case UNSUBSCRIBE:
                    userParticipateExitService.processExit(message);
                    break;
            }
        } catch (Exception e) {
            // 예외가 발생한 경우 subscribe 시키지 않습니다.
            e.printStackTrace();
            message = null;
        } finally {
            return message;
        }
    }
}
