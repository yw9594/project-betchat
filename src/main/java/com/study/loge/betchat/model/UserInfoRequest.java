package com.study.loge.betchat.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoRequest {
    String userName;
    LocalDateTime transactionTime;
}
