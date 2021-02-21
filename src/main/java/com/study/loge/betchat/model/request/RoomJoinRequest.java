package com.study.loge.betchat.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 채팅방 참가 요청 정보를 전달합니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomJoinRequest {
    private String roomKey; // 참가하고자 하는 채팅방의 roomKey.
}
