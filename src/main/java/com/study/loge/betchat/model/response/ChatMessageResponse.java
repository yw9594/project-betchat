package com.study.loge.betchat.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 유저가 보내는 채팅 메시지를 broadcast하기 위한 클래스입니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResponse {
    String userName;
    String text;
}
