package com.study.loge.betchat.component;

import com.study.loge.betchat.service.UserParticipateExitService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// 메세지가 Controller 또는 Broker로 전달하기 전 처리하는 클래스입니다.
@AllArgsConstructor
@Component
public class StompInboundChannelInterceptor implements ChannelInterceptor {
    private UserParticipateExitService userParticipateExitService;

    // 유저가 채팅방에 참가/퇴장 시 로직을 제어합니다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);
        SimpMessageType messageType = stompHeaderAccessor.getMessageType();

        try {
            // message type에 따라 특정 로직을 수행합니다.
            if(SimpMessageType.SUBSCRIBE.equals(messageType) )
                userParticipateExitService.processParticipate(message);
            else if(SimpMessageType.DISCONNECT.equals(messageType) || SimpMessageType.UNSUBSCRIBE.equals(messageType))
                userParticipateExitService.processExit(message);

        } catch (Exception e) {
            // 예외가 발생한 경우 subscribe 시키지 않습니다.
            e.printStackTrace();
            message = null;
        } finally {
            return message;
        }
    }
}
