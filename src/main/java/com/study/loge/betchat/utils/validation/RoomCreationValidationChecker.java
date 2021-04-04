package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.entity.User;
import com.study.loge.betchat.exception.RoomCreateException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomCreateRequest;
import com.study.loge.betchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

// 채팅방 생성 요청의 유효성 검사를 수행합니다.
@Component
@AllArgsConstructor
public class RoomCreationValidationChecker extends AbstractMessageHeaderValidationChecker {
    private UserRepository userRepository;

    @Override
    public void isValid(MessageHeader messageHeader) throws Exception {
        MessageHeader<RoomCreateRequest> request = (MessageHeader<RoomCreateRequest>) messageHeader;

        checkUserKey(request);
    }

    private void checkUserKey(MessageHeader<RoomCreateRequest> request) throws RoomCreateException {
        String userKey = request.getData().getUserKey();
        User user = userRepository.findByUserKey(userKey);

        if(user==null) throw new RoomCreateException();
    }
}
