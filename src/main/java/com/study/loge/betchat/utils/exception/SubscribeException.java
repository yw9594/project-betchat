package com.study.loge.betchat.utils.exception;

import lombok.NoArgsConstructor;

// 채팅방 참여 시 발생하는 예외를 나타내는 클래스입니다.
@NoArgsConstructor
public class SubscribeException extends Exception{
    public SubscribeException(String message) {
        super(message);
    }
}
