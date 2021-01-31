package com.study.loge.betchat.controller;

import com.study.loge.betchat.enums.MessageStatus;
import com.study.loge.betchat.model.requests.UserInfoRequest;
import com.study.loge.betchat.model.response.UserInfoResponse;
import com.study.loge.betchat.model.MessageHeader;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/submit")
    public MessageHeader<UserInfoResponse> submit(@RequestBody MessageHeader<UserInfoRequest> request){
        UserInfoResponse userInfoResponse = UserInfoResponse
                .builder()
                .userId("userId")
                .build();

        MessageHeader<UserInfoResponse> response = MessageHeader.makeMessage(MessageStatus.OK, userInfoResponse);

        return response;
    }
}
