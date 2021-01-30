package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/hello")
    public UserInfo hello(@RequestBody UserInfo userInfo){

        return userInfo;
    }
}
