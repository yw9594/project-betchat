package com.study.loge.betchat.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

// userKey, roomKey를 생성하기 위한 key 생성자입니다.
@Component
public class KeyGenerator {
    public String generateKey(){
        return UUID.randomUUID().toString();
    }
}
