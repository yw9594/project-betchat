package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.requests.UserInfoRequest;
import com.study.loge.betchat.model.response.UserInfoResponse;
import com.study.loge.betchat.model.MessageHeader;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/submit")
    public MessageHeader<UserInfoResponse> submit(@RequestBody MessageHeader<UserInfoRequest> request){
        System.out.println(request);

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .userId("userId")
                .build();

        MessageHeader<UserInfoResponse> response = MessageHeader.makeMessage(userInfoResponse);

        return response;
    }
}
