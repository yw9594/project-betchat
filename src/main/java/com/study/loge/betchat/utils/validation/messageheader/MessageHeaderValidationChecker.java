package com.study.loge.betchat.utils.validation.messageheader;

import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.utils.validation.ValidationChecker;

// MessageHeader 유효성 검사를 위한 인터페이스 입니다.
public interface MessageHeaderValidationChecker extends ValidationChecker {
     public void check(MessageHeader messageHeader) throws Exception;
}
