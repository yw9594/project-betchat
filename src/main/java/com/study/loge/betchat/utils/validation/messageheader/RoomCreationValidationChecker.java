package com.study.loge.betchat.utils.validation.messageheader;

import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.monitor.RoomEntranceCounter;
import com.study.loge.betchat.utils.exception.RoomCreateException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomCreateRequest;
import com.study.loge.betchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 채팅방 생성 요청의 유효성 검사를 수행합니다.
@Component
@RequiredArgsConstructor
public class RoomCreationValidationChecker extends AbstractMessageHeaderValidationChecker {
    @Value("${room.create-limit}")
    private Integer ROOM_CREATE_LIMIT;

    private final RoomEntranceCounter roomEntranceCounter;
    private final UserRepository userRepository;

    @Override
    public void isValid(MessageHeader messageHeader) throws Exception {
        MessageHeader<RoomCreateRequest> request = (MessageHeader<RoomCreateRequest>) messageHeader;

        checkUserKey(request);
        checkRoomCreateLimit();
    }

    private void checkUserKey(MessageHeader<RoomCreateRequest> request) throws RoomCreateException {
        String userKey = request.getData().getUserKey();
        User user = userRepository.findByUserKey(userKey);

        if(user==null) throw new RoomCreateException();
    }
    private void checkRoomCreateLimit() throws RoomCreateException {
        if(roomEntranceCounter.getActiveRoomCount() >= ROOM_CREATE_LIMIT) throw new RoomCreateException();
    }
}
