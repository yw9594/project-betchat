package com.study.loge.betchat.service.dao;

import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.repository.UserRepository;
import com.study.loge.betchat.utils.KeyGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// User 테이블 Dao입니다.
@Component
@AllArgsConstructor
public class UserDao {
    private final KeyGenerator keyGenerator;
    private UserRepository userRepository;

    // 사용자를 데이터베이스에 저장하고 UserKey를 저장합니다.
    public String create(String userName){
        String userKey = keyGenerator.generateKey();

        User user = User.builder()
                .userName(userName)
                .userKey(userKey)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return userKey;
    }
}
