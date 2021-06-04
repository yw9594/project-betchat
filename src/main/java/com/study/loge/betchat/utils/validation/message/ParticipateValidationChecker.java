package com.study.loge.betchat.utils.validation.message;

import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.repository.RoomRepository;
import com.study.loge.betchat.repository.UserRepository;
import com.study.loge.betchat.utils.exception.SubscribeException;
import com.study.loge.betchat.utils.parser.StompHeaderParser;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

// 참가 요청에 대한 유효성을 검사합니다.
@AllArgsConstructor
@Component
public class ParticipateValidationChecker extends AbstractMessageValidationChecker{
    private UserRepository userReposotory;
    private RoomRepository roomRepository;

    private StompHeaderParser stompHeaderParser;

    @Override
    public void isValid(Message<?> message) throws Exception {
        isKeyValidationCheck(message);
    }

    private void isKeyValidationCheck(Message<?> message) throws SubscribeException {
        String userKey = StompHeaderParser.getUserKey(message);
        String roomKey = StompHeaderParser.getRoomKey(message);

        User user = userReposotory.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        // 유효하지 않은 user 또는 room일 경우 예외를 발생시킵니다.
        if(user==null || room==null) throw new SubscribeException("유효하지 않은 user key 또는 room key입니다.");
    }
}
