package com.study.loge.betchat.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// 모든 JSON 통신에 사용되는 메시지의 Header 클래스를 정의합니다.
@Data
@Builder
public class MessageHeader<T> {
    private LocalDateTime transactionTime;  // 메시지 발송 시간
    T data;                                 // 메시지 body

    public static <T> MessageHeader<T> makeMessage(T data){
        MessageHeader<T> message = MessageHeader.<T>builder()
                .transactionTime(LocalDateTime.now())
                .data(data)
                .build();
        return message;
    }
}
