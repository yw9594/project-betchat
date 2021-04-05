package com.study.loge.betchat.utils.validation.messageheader;

import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.utils.exception.RoomJoinException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomJoinRequest;
import com.study.loge.betchat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

// 채티방 참가 요청 유효성 검사를 수행합니다.
@Component
@AllArgsConstructor
public class RoomJoinValidationChecker extends AbstractMessageHeaderValidationChecker{
    private RoomRepository roomRepository;

    @Override
    public void isValid(MessageHeader messageHeader) throws Exception {
        MessageHeader<RoomJoinRequest> request = (MessageHeader<RoomJoinRequest>) messageHeader;

        checkRoomKey(request);
    }
    private void checkRoomKey(MessageHeader<RoomJoinRequest> request) throws RoomJoinException {
        String roomKey = request.getData().getRoomKey();
        Room room = roomRepository.findByRoomKey(roomKey);

        if(room==null) throw new RoomJoinException();
    }
}
