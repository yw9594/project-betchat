package com.study.loge.betchat.service;

import com.study.loge.betchat.utils.KeyGenerator;
import com.study.loge.betchat.entity.Room;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.exception.RoomCreateException;
import com.study.loge.betchat.exception.RoomJoinException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomCreateRequest;
import com.study.loge.betchat.model.request.RoomJoinRequest;
import com.study.loge.betchat.model.response.RoomCreateResponse;
import com.study.loge.betchat.model.response.RoomJoinResponse;
import com.study.loge.betchat.repository.RoomRepository;
import com.study.loge.betchat.utils.validation.messageheader.RoomCreationValidationChecker;
import com.study.loge.betchat.utils.validation.messageheader.RoomJoinValidationChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 로비 페이지에서의 로직을 정의합니다.
@AllArgsConstructor
@Service
public class LobbyService {
    // userId를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;
    private final RoomRepository roomRepository;

    private final RoomCreationValidationChecker roomCreationValidationChecker;
    private final RoomJoinValidationChecker roomJoinValidationChecker;

    // 방 생성 요청을 받고 유저에게 Room Key를 전달합니다.
    public MessageHeader<RoomCreateResponse> createRoom(MessageHeader<RoomCreateRequest> request) {
        ResultState resultState = null;
        String roomKey = null;

        try {
            roomCreationValidationChecker.check(request);

            resultState = ResultState.OK;
            roomKey = keyGenerator.generateKey();

            Room room = Room.builder()
                    .roomKey(roomKey)
                    .createdAt(LocalDateTime.now())
                    .build();

            roomRepository.save(room);
        } catch (RoomCreateException e) {
            resultState = ResultState.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
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
