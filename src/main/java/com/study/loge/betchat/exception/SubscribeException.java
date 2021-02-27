package com.study.loge.betchat.exception;

import lombok.NoArgsConstructor;

// Subscribe 시 발생하는 예외를 나타내는 클래스입니다.
@NoArgsConstructor
public class SubscribeException extends Exception{
    public SubscribeException(String message) {
        super(message);
    }
}
