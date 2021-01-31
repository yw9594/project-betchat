package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.UserInfoRequest;
import com.study.loge.betchat.model.UserInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/submit")
    public UserInfoResponse submit(@RequestBody UserInfoRequest userInfoRequest){
        String userId = userInfoRequest.getUserName();
        LocalDateTime transactionTime = userInfoRequest.getTransactionTime();

        UserInfoResponse userInfoResponse = UserInfoResponse.builder().userId(userId).transactionTime(transactionTime).build();
        return userInfoResponse;
    }
}
