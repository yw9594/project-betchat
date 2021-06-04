package com.study.loge.betchat.utils.validation.message;

import org.springframework.messaging.Message;

// Message 클래스 유효성 검사 기능을 제공합니다.
public interface MessageValidationChecker {
    void check(Message<?> message) throws Exception;
}
