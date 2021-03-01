package com.study.loge.betchat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 클라이언트에 메시지 상태를 알려주는 클래스입니다.
public enum ResultState {
    OK,
    ERROR;
}
