package com.study.loge.betchat.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    String userName;
    LocalDateTime transactionTime;
}
