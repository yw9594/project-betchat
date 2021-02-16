package com.study.loge.betchat.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 채팅방 생성 후 클라이언트에게 room key를 전달합니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomCreateResponse {
    private String roomKey; // 채팅방 키
}
