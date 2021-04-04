package com.study.loge.betchat.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

// userKey, roomKey를 생성하기 위한 키 생성자입니다.
@Component
public class KeyGenerator {
    public String generateKey(){
        return UUID.randomUUID().toString();
    }
}
