package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.exception.ChatMessageTextException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.ChatMessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

// 채팅 메시지의 유효성을 검사합니다.
@Component
@AllArgsConstructor
public class ChatMessageValidationChecker extends AbstractMessageHeaderValidationChecker{
    @Override
    public void isValid(MessageHeader messageHeader) throws Exception {
        MessageHeader<ChatMessageRequest> request = (MessageHeader)messageHeader;
        checkText(request);
    }
    void checkText(MessageHeader<ChatMessageRequest> request) throws ChatMessageTextException {
        String text = request.getData().getText();
        if(text.length()<1 || text.length()>100) throw new ChatMessageTextException();
    }

}
