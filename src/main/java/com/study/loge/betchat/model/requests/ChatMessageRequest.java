package com.study.loge.betchat.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 유저가 보내는 채팅 메시지를 처리합니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageRequest {
    private String userName;
    private String userKey;
    private String roomKey;
    private String text;
}
