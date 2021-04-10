package com.study.loge.betchat.monitor;

import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// 채팅방 참가 및 퇴장 시 발생하는 정보를 관리합니다.
@AllArgsConstructor
@Component
public class RoomEntranceMonitor implements ChannelInterceptor {
    private RoomEntranceManager roomEntranceManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);
        SimpMessageType messageType = stompHeaderAccessor.getMessageType();

        // Stomp Message Type에 따라 정보를 처리합니다.
        try {
            if(SimpMessageType.SUBSCRIBE.equals(messageType) )
                roomEntranceManager.processParticipate(message);
            else if(SimpMessageType.DISCONNECT.equals(messageType))
                roomEntranceManager.processExit(message);
        }
        // 예외가 발생한 경우 subscribe 시키지 않습니다.
        catch (Exception e) {
            message = null;
        }
        finally {
            return message;
        }
    }
}
