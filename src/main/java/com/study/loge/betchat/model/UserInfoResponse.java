package com.study.loge.betchat.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoResponse {
    String userId;
    LocalDateTime transactionTime;
}
