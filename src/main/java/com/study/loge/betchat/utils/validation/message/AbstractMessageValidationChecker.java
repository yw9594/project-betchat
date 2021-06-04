package com.study.loge.betchat.utils.validation.message;

import org.springframework.messaging.Message;

public abstract class AbstractMessageValidationChecker implements MessageValidationChecker{
    @Override
    public void check(Message<?> message) throws Exception {
        isValid(message);
    }
    abstract void isValid(Message<?> message) throws Exception;
}
