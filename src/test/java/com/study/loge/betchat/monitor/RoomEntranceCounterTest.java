package com.study.loge.betchat.monitor;

import com.study.loge.betchat.utils.parser.StompHeaderParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
public class RoomEntranceCounterTest {
    @Autowired
    private RoomEntranceCounter roomEntranceCounter;

    private final String userKey = "userKey";
    private final String userName = "userName";
    private final String roomKey = "roomKey";
    private final String simpSessionId = "testSess";

    private final StompCommand stompCommand = StompCommand.SEND;
    private Message<?> message;

    @Test
    public void disabledRoomAccessTest(){

    }
    @Test
    public void raceConditionTest() {

    }

//    @BeforeEach
    @Test
    public void createTestMessge(){
        StompHeaderAccessor accessor = StompHeaderAccessor.create(stompCommand);
        accessor.setNativeHeader("user_key", userKey);
        accessor.setNativeHeader("room_key", roomKey);
        accessor.setSessionId(simpSessionId);

        MessageHeaders stompHeaders = accessor.getMessageHeaders();
        message = MessageBuilder.withPayload(new Object()).copyHeaders(stompHeaders).build();

        // null pointer error!
        System.out.println(StompHeaderParser.getUserKey(message));
    }
}
