package com.study.loge.betchat.service;

import com.study.loge.betchat.component.KeyGenerator;
import com.study.loge.betchat.entity.User;
import com.study.loge.betchat.enums.ResultState;
import com.study.loge.betchat.enums.UserLoginValidationState;
import com.study.loge.betchat.exceptions.UserLoginException;
import com.study.loge.betchat.model.MessageHeader;
import com.study.loge.betchat.model.requests.UserLoginRequest;
import com.study.loge.betchat.model.response.UserLoginResponse;
import com.study.loge.betchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

// user login 로직을 처리합니다.
@AllArgsConstructor
@Service
public class UserLoginService {
    // userKey를 생성하기 위한 객체입니다.
    private final KeyGenerator keyGenerator;

    // DB에 접근하기 위한 repository 객체입니다.
    private UserRepository userRepository;

    // 유저의 로그인 요청을 처리합니다.
    public MessageHeader<UserLoginResponse> userLogin(MessageHeader<UserLoginRequest> request){
        ResultState resultState = null;
        String userKey = null;

        String userName = request.getData().getUserName();
        try {
            // userName의 유효성을 검사합니다.
            checkUserNameValidation(userName);

            // response에 필요한 값을 생성합니다.
            resultState = ResultState.OK;
            userKey = keyGenerator.generateKey();

            // DB에 저장합니다.
            User user = User.builder()
                    .userName(userName)
                    .userKey(userKey)
                    .activated(1)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
        catch(UserLoginException e){
            resultState = ResultState.ERROR;
        }
        finally {
            // response를 생성한 뒤, 반환한다.
            UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                    .userKey(userKey)
                    .build();
            return MessageHeader.makeMessage(resultState, userLoginResponse);
        }
    }

    // userName의 유효성을 검사합니다.
    private void checkUserNameValidation(String userName) throws UserLoginException {
        String pattern = "^[가-힣a-zA-Z0-9_]{2,8}$";

        if(!userName.matches(pattern))
            throw new UserLoginException();
    }
}
