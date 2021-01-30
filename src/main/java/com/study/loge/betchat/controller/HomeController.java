package com.study.loge.betchat.controller;

import com.study.loge.betchat.model.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @PostMapping("/submit")
    public UserInfo submit(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        return userInfo;
    }
}
