package com.study.loge.betchat.controller;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.model.requests.UserLoginRequest;
import com.study.loge.betchat.model.response.UserLoginResponse;
import com.study.loge.betchat.model.MessageHeader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Home URL 경로를 제어합니다.
@AllArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {
    // userKey를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    @PostMapping("/submit")
    public MessageHeader<UserLoginResponse> userLogin(@RequestBody MessageHeader<UserLoginRequest> request){
        String userKey = keyGenerator.generateKey();
        UserLoginResponse userLoginResponse = UserLoginResponse
                .builder()
                .userKey(userKey)
                .build();
        MessageHeader<UserLoginResponse> response = MessageHeader.makeMessage(ResultState.OK, userLoginResponse);

        return response;
    }
}
