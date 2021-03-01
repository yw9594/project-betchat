package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.ChatMessageRequest;
import com.study.loge.betchat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 채팅방 페이지의 요청을 제어합니다.
@AllArgsConstructor
@RestController
public class ChatController {
    private ChatService chatService;

    @MessageMapping("/")
    public void broadcastMessage(@RequestBody MessageHeader<ChatMessageRequest> request){
        chatService.broadcastMessage(request);
    }
}
