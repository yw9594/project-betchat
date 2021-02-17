package com.study.loge.betchat.service;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.enums.UserLoginValidationState;
import com.study.loge.betchat.exceptions.UserLoginException;
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
        ResultState resultState=null;

        String userName = request.getData().getUserName();
        try {
            // userName의 유효성을 검사합니다.
            checkUserNameValidation(userName);

            resultState = ResultState.OK;
        }
        catch(UserLoginException e){
            resultState = ResultState.ERROR;
        }
        finally {
            // response를 생성해 전달합니다.
            return MessageHeader.makeMessage(resultState, createUserLoginResponse(resultState));
        }
    }

    // userName의 유효성을 검사합니다.
    private void checkUserNameValidation(String userName) throws UserLoginException {
        String pattern = "\\[가-힣a-Z0-9\\]+";
        int userNameLength = userName.length();

        if(userNameLength<2 || userNameLength>8 || userName.matches(pattern))
            throw new UserLoginException();
    }

    // resultState에 따른 UserLoginResponse 객체를 생성합니다.
    private UserLoginResponse createUserLoginResponse(ResultState resultState){
        String userKey = resultState==ResultState.OK ? keyGenerator.generateKey() : null;

        return UserLoginResponse.builder()
                .userKey(userKey)
                .build();
    }
}
