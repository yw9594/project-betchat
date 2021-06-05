package com.study.loge.betchat.database.repository;

import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    private final String userName = "testName";
    private final String tooLongUserName = "TooLongTestNameForTestHowThisUserCanChatOnChattingRoom";
    private final String userKey = "testKey";

    @Test
    public void createTest() {
        User user = User.builder().userName(userName).userKey(userKey).createdAt(LocalDateTime.now()).build();

        User newUser = userRepository.save(user);

        logger.info(newUser.toString());
    }

    @Test
    public void readTest() {
        User newUser = User.builder().userName(userName).userKey(userKey).createdAt(LocalDateTime.now()).build();

        userRepository.save(newUser);

        User user = userRepository.findByUserKey(userKey);

        logger.info(user.toString());
    }

    @Test
    public void constraintRedundantUserKeyTest() {
        User user1 = User.builder().userName(userName).userKey(userKey).createdAt(LocalDateTime.now()).build();
        User user2 = User.builder().userName(userName).userKey(userKey).createdAt(LocalDateTime.now()).build();

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            userRepository.save(user1);
            userRepository.save(user2); // 제약 조건 예외 예상.
        });
    }

    @Test
    public void constraintTooLongUserNameTest() {
        User user = User.builder().userName(tooLongUserName).userKey(userKey).createdAt(LocalDateTime.now()).build();

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            userRepository.save(user); // 제약 조건 예외 예상.
        });
    }

    @BeforeEach
    public void deleteTestUser() {
        User user = userRepository.findByUserKey(userKey);
        if(user!=null) userRepository.delete(user);
    }
}
