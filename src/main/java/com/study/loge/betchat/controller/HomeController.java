package com.study.loge.betchat.controller;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.MessageStatus;
import com.study.loge.betchat.model.requests.UserInfoRequest;
import com.study.loge.betchat.model.response.UserInfoResponse;
import com.study.loge.betchat.model.MessageHeader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Home URL 경로를 제어합니다.
@AllArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {
    // userId를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    @PostMapping("/submit")
    public MessageHeader<UserInfoResponse> submit(@RequestBody MessageHeader<UserInfoRequest> request){
        String userKey = keyGenerator.generateKey();
        UserInfoResponse userInfoResponse = UserInfoResponse
                .builder()
                .userKey(userKey)
                .build();
        MessageHeader<UserInfoResponse> response = MessageHeader.makeMessage(MessageStatus.OK, userInfoResponse);

        return response;
    }
}
