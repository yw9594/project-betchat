package com.study.loge.betchat.controller;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.RoomCreateRequest;
import com.study.loge.betchat.model.response.RoomCreateResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Lobby URL 경로를 제어합니다.
@AllArgsConstructor
@RestController
@RequestMapping("/lobby")
public class LobbyController {
    // userId를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    // 방 생성 요청을 받고 유저에게 Room Key를 전달합니다.
    @PostMapping("/create")
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
