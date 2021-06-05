package com.study.loge.betchat.utils.parser;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

// Stomp 메시지의 헤더 값을 반환합니다.
public class StompHeaderParser {
    public static String getRoomKey(Message<?> message){
        return getStompHeaderAccessor(message).getNativeHeader("room_key").get(0);
    }
    public static String getUserKey(Message<?> message){
        return getStompHeaderAccessor(message).getNativeHeader("user_key").get(0);
    }
    public static String getSimpSessionId(Message<?> message){
        return getStompHeaderAccessor(message).getSessionId();
    }
    public static SimpMessageType getSimpMessageType(Message<?> message){
        return getStompHeaderAccessor(message).getMessageType();
    }
    private static StompHeaderAccessor getStompHeaderAccessor(Message<?> message) {
        return (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);
    }
}
