package com.study.loge.betchat.utils.validation;

import com.study.loge.betchat.exception.UserLoginException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.request.UserLoginRequest;
import org.springframework.stereotype.Component;

// 로그인 유효성 검사 클래스입니다.
@Component
public class LoginValidationChecker extends AbstractMessageHeaderValidationChecker{
    @Override
    public void isValid(MessageHeader messageHeader) throws UserLoginException {
        MessageHeader<UserLoginRequest> request = (MessageHeader<UserLoginRequest>)messageHeader;
        String userName = request.getData().getUserName();

        checkUserName(userName);
    }

    // userName의 유효성을 검사합니다.
    private void checkUserName(String userName) throws UserLoginException {
        String pattern = "^[가-힣ㄱ-ㅎa-zA-Z0-9_]{2,6}$";

        if(!userName.matches(pattern))
            throw new UserLoginException();
    }
}
