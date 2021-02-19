package com.study.loge.betchat.service;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.RoomCreateRequest;
import com.study.loge.betchat.model.response.RoomCreateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// room create 로직을 처리합니다.
@AllArgsConstructor
@Service
public class RoomCreateService {
    // userId를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    // 방 생성 요청을 받고 유저에게 Room Key를 전달합니다.
    public MessageHeader<RoomCreateResponse> createRoom(@RequestBody MessageHeader<RoomCreateRequest> request){
        String roomKey = keyGenerator.generateKey();
        RoomCreateResponse roomCreateResponse = RoomCreateResponse
                .builder()
                .roomKey(roomKey)
                .build();
        MessageHeader<RoomCreateResponse> response = MessageHeader.makeMessage(ResultState.OK, roomCreateResponse);

        return response;
    }

}
