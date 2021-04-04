package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.model.MessageHeader;

// 유효성 검사를 확장하기 위한 추상 클래스입니다.
public abstract class AbstractMessageHeaderValidationChecker implements MessageHeaderValidationChecker {
    @Override
    final public boolean check(MessageHeader messageHeader) {
        return isValid(messageHeader);
    }
    abstract protected boolean isValid(MessageHeader messageHeader);
}
