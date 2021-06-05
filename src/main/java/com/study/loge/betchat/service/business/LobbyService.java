package com.study.loge.betchat.service.business;

import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomCreateRequest;
import com.study.loge.betchat.model.request.RoomJoinRequest;
import com.study.loge.betchat.model.response.RoomCreateResponse;
import com.study.loge.betchat.model.response.RoomJoinResponse;
import com.study.loge.betchat.service.dao.RoomDao;
import com.study.loge.betchat.utils.enums.ResultState;
import com.study.loge.betchat.utils.exception.RoomJoinException;
import com.study.loge.betchat.utils.validation.messageheader.RoomCreationValidationChecker;
import com.study.loge.betchat.utils.validation.messageheader.RoomJoinValidationChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// 로비 페이지에서의 로직을 정의합니다.
@AllArgsConstructor
@Service
public class LobbyService {
    // userId를 생성하기 위한 객체입니다.
    private final RoomDao roomDao;

    private final RoomCreationValidationChecker roomCreationValidationChecker;
    private final RoomJoinValidationChecker roomJoinValidationChecker;

    // 방 생성 요청을 받고 유저에게 Room Key를 전달합니다.
    public MessageHeader<RoomCreateResponse> createRoom(MessageHeader<RoomCreateRequest> request) {
        ResultState resultState = null;
        String roomKey = null;

        try {
            roomCreationValidationChecker.check(request);

            resultState = ResultState.OK;
            roomKey = roomDao.create();
        } catch (Exception e) {
            resultState = ResultState.ERROR;
            roomKey = null;
        } finally {
            RoomCreateResponse roomCreateResponse = RoomCreateResponse
                    .builder()
                    .roomKey(roomKey)
                    .build();
            MessageHeader<RoomCreateResponse> response = MessageHeader.makeMessage(resultState, roomCreateResponse);

            return response;
        }
    }

    // 채팅방 참가 요청을 받고 유저에게 처리 결과를 전달합니다.
    public MessageHeader<RoomJoinResponse> joinRoom(MessageHeader<RoomJoinRequest> request) {
        ResultState resultState = null;

        try {
            roomJoinValidationChecker.check(request);
            resultState = ResultState.OK;
        } catch (RoomJoinException e) {
            resultState = ResultState.ERROR;
        } finally {
            MessageHeader<RoomJoinResponse> response = MessageHeader.makeMessage(resultState, null);

            return response;
        }
    }
}
