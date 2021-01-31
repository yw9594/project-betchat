package com.study.loge.betchat.controller;

import com.study.loge.betchat.enums.MessageStatus;
import com.study.loge.betchat.model.requests.UserInfoRequest;
import com.study.loge.betchat.model.response.UserInfoResponse;
import com.study.loge.betchat.model.MessageHeader;
import org.springframework.web.bind.annotation.*;

// Home URL 경로를 제어합니다.
@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/submit")
    public MessageHeader<UserInfoResponse> submit(@RequestBody MessageHeader<UserInfoRequest> request){
        // 유저 정보의 유효성을 검사합니다.
        
        
        // 유저에게 전달할 response를 생성합니다.
        UserInfoResponse userInfoResponse = UserInfoResponse
                .builder()
                .userId("userId")
                .build();
        MessageHeader<UserInfoResponse> response = MessageHeader.makeMessage(MessageStatus.OK, userInfoResponse);

        return response;
    }
}
