package com.study.loge.betchat.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 클라이언트로부터 user name을 전달받기 위한 클래스입니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {
    private String userName;
}
