package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.model.MessageHeader;

// 유효성 검사를 위한 인터페이스입니다.
public interface MessageHeaderValidationChecker {
    public boolean check(MessageHeader messageHeader);
}
