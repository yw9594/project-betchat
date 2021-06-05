package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.RoomCreateRequest;
import com.study.loge.betchat.model.request.RoomJoinRequest;
import com.study.loge.betchat.model.response.RoomCreateResponse;
import com.study.loge.betchat.model.response.RoomJoinResponse;
import com.study.loge.betchat.service.business.LobbyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 페이지의 요청을 제어합니다.
@AllArgsConstructor
@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private LobbyService lobbyService;

    // 방 생성 요청을 받고 유저에게 Room Key를 전달합니다.
    @PostMapping("/create")
    public MessageHeader<RoomCreateResponse> createRoom(@RequestBody MessageHeader<RoomCreateRequest> request){
        return lobbyService.createRoom(request);
    }
    // 방 참가 요청을 받고 유효성 검사 여부를 전달합니다.
    @PostMapping("/join")
    public MessageHeader<RoomJoinResponse> joinRoom(@RequestBody MessageHeader<RoomJoinRequest> request){
        return lobbyService.joinRoom(request);
    }
}
