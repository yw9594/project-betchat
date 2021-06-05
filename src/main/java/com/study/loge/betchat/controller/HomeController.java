package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.request.UserLoginRequest;
import com.study.loge.betchat.model.response.UserLoginResponse;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.service.business.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 홈 페이지의 경로를 제어합니다.
@AllArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    // 유저의 로그인 요청을 처리합니다.
    @PostMapping("/login")
    public MessageHeader<UserLoginResponse> userLogin(@RequestBody MessageHeader<UserLoginRequest> request){
        return homeService.userLogin(request);
    }
}
