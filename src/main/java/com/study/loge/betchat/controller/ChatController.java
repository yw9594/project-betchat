package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.ChatMessageRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

// 전달된 채팅을 처리하는 controller 클래스입니다.
@AllArgsConstructor
@NoArgsConstructor
@RestController
public class ChatController {

    // Controller에서 Broker를 사용해 메시지를 broadcast합니다.
    private SimpMessagingTemplate template;


    @MessageMapping("/chatting")
    public void broadcastMessage(MessageHeader<ChatMessageRequest> request){
        ChatMessageRequest message = request.getData();

        template.convertAndSend("/sub/chat"+message.getRoomKey(), message.getText());
    }
}
