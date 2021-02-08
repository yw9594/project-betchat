package com.study.loge.betchat.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KeyGenerator {
    public String generateKey(){
        return UUID.randomUUID().toString();
    }
}
