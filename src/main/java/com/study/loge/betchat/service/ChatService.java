package com.study.loge.betchat.service;

import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.ChatMessageRequest;
import com.study.loge.betchat.model.response.ChatMessageResponse;
import com.study.loge.betchat.utils.validation.ChatMessageValidationChecker;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

// 채팅 처리 로직을 정의하는 클래스입니다.
@AllArgsConstructor
@Service
public class ChatService {
    private SimpMessagingTemplate template;
    private ChatMessageValidationChecker chatMessageValidationChecker;

    public void broadcastMessage(@RequestBody MessageHeader<ChatMessageRequest> request){

        // 전달받은 채팅을 broker로 전달해 broadcast합니다.
        try {
            chatMessageValidationChecker.check(request);

            String roomKey = request.getData().getRoomKey();
            ChatMessageResponse message = ChatMessageResponse.builder()
                    .userName(request.getData().getUserName())
                    .text(request.getData().getText())
                    .build();
            MessageHeader<ChatMessageResponse> response = MessageHeader.makeMessage(ResultState.OK, message);
            template.convertAndSend("/sub/chat/"+roomKey, response);
        }
        // 유효하지 않은 메시지는 전송하지 않습니다.
        catch (Exception e) {
        }
    }
}
