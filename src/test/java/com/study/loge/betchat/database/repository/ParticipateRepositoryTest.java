package com.study.loge.betchat.database.repository;

import com.study.loge.betchat.model.entity.Participate;
import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.repository.ParticipateRepository;
import com.study.loge.betchat.repository.RoomRepository;
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
public class ParticipateRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ParticipateRepository participateRepository;

    private Logger logger = LoggerFactory.getLogger(ParticipateRepositoryTest.class);

    private final String userKey = "userKey";
    private final String userName = "userName";
    private final String roomKey = "roomKey";
    private final String simpSessionId = "testSess";

    @Test
    public void createTest() {
        User user = userRepository.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        Participate participate = Participate.builder().user(user).room(room).isJoined(1).joinedAt(LocalDateTime.now()).simpSessionId(simpSessionId).build();

        Participate newParticipate = participateRepository.save(participate);

        logger.info(newParticipate.toString());
    }

    @Test
    public void update() {
        User user = userRepository.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        Participate newParticipate = Participate.builder().user(user).room(room).isJoined(1).joinedAt(LocalDateTime.now()).simpSessionId(simpSessionId).build();
        participateRepository.save(newParticipate);

        Participate participate = participateRepository.findBySimpSessionId(simpSessionId);
        participate.setIsJoined(0);
        participate.setExitedAt(LocalDateTime.now());

        Participate updatedParticipate = participateRepository.save(participate);
        logger.info(updatedParticipate.toString());
    }

    @Test
    public void constraintRedundantSimpSessionIdTest() {
        User user = userRepository.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        Participate participate1 = Participate.builder().user(user).room(room).isJoined(1).joinedAt(LocalDateTime.now()).simpSessionId(simpSessionId).build();
        Participate participate2 = Participate.builder().user(user).room(room).isJoined(1).joinedAt(LocalDateTime.now()).simpSessionId(simpSessionId).build();

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            participateRepository.save(participate1);
            participateRepository.save(participate2); // 제약 조건 예외 예상.
        });
    }

    @BeforeEach
    public void createTestParticipate() {
        User user = User.builder().userName(userName).userKey(userKey).createdAt(LocalDateTime.now()).build();
        Room room = Room.builder().roomKey(roomKey).createdAt(LocalDateTime.now()).build();

        userRepository.save(user);
        roomRepository.save(room);
    }

    @AfterEach
    public void deleteTestParticipate() {
        Participate participate = participateRepository.findBySimpSessionId(simpSessionId);
        if(participate!=null) participateRepository.delete(participate);

        Room room = roomRepository.findByRoomKey(roomKey);
        if(room!=null) roomRepository.delete(room);

        User user = userRepository.findByUserKey(userKey);
        if(user!=null) userRepository.delete(user);
    }
}
