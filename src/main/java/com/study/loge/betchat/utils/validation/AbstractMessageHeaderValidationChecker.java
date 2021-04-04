package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.model.MessageHeader;

// MessageHeader 유효성 검사 형식을 정의한 추상 클래스입니다.
public abstract class AbstractMessageHeaderValidationChecker implements MessageHeaderValidationChecker {
    final public void check(MessageHeader messageHeader) throws Exception {
        isValid(messageHeader);
    }
    abstract public void isValid(MessageHeader messageHeader) throws Exception;
}
