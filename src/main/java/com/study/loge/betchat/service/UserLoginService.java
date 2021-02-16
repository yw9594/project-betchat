package com.study.loge.betchat.service;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.enums.UserLoginValidationState;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.UserLoginRequest;
import com.study.loge.betchat.model.response.UserLoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// user login 로직을 처리합니다.
@AllArgsConstructor
@Service
public class UserLoginService {
    // userKey를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    // 유저의 로그인 요청을 처리합니다.
    public MessageHeader<UserLoginResponse> userLogin(MessageHeader<UserLoginRequest> request){
        // userName의 유효성을 검사합니다.
        String userName = request.getData().getUserName();
        UserLoginValidationState userLoginValidationState = checkUserNameValidation(userName);

        // response를 생성해 전달합니다.
        String userKey = (userLoginValidationState==UserLoginValidationState.OK) ? keyGenerator.generateKey() : null;
        ResultState resultState = (userLoginValidationState==UserLoginValidationState.OK) ? ResultState.OK : ResultState.ERROR;
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .userKey(userKey)
                .build();

        MessageHeader<UserLoginResponse> response = MessageHeader.makeMessage(resultState, userLoginResponse);

        return response;
    }

    // userName의 유효성을 검사합니다.
    private UserLoginValidationState checkUserNameValidation(String userName){
        String pattern = "\\[가-힣a-Z0-9\\]+";
        UserLoginValidationState result;

        if(userName.length()<2 || userName.length()>8 )
            result = UserLoginValidationState.TOO_SHORT;
        else if(userName.matches(pattern))
            result =  UserLoginValidationState.INVALID_CHAR;
        else
            result =  UserLoginValidationState.OK;

        return result;
    }
}
