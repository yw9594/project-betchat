package com.study.loge.betchat.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 채팅방을 생성하길 바라는 클라이언트로부터 user key를 전달받는 클래스입니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomCreateRequest {
    private String userKey;  // 채팅방 생성 요청 유저 Key
}
