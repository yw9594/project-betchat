package com.study.loge.betchat.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 클라이언트에게 user key를 전달하기 위한 클래스입니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private String userKey;
}
