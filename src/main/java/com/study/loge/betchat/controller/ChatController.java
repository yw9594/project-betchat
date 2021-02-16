package com.study.loge.betchat.controller;

import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.ChatMessageRequest;
import com.study.loge.betchat.model.response.ChatMessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 전달된 채팅을 처리하는 controller 클래스입니다.
@AllArgsConstructor
@RestController
public class ChatController {

    // Controller에서 Broker를 사용해 메시지를 broadcast합니다.
    private SimpMessagingTemplate template;

    @MessageMapping("/")
    public void broadcastMessage(@RequestBody MessageHeader<ChatMessageRequest> request){
        String roomKey = request.getData().getRoomKey();
        ChatMessageResponse message = ChatMessageResponse.builder()
                    .userName(request.getData().getUserName())
                    .text(request.getData().getText())
                    .build();
        MessageHeader<ChatMessageResponse> response = MessageHeader.makeMessage(ResultState.OK, message);

        // 전달받은 채팅을 broker로 전달해 broadcast합니다.
        template.convertAndSend("/sub/chat/"+roomKey, response);
    }
}
