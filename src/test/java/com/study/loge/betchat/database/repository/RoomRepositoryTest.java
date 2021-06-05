package com.study.loge.betchat.database.repository;

import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.repository.RoomRepository;
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
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;
    private Logger logger = LoggerFactory.getLogger(RoomRepositoryTest.class);

    private final String roomKey = "testKey";

    @Test
    public void createTest() {
        Room room = Room.builder().roomKey(roomKey).createdAt(LocalDateTime.now()).build();

        Room newRoom = roomRepository.save(room);

        logger.info(newRoom.toString());
    }

    @Test
    public void readTest() {
        Room newRoom = Room.builder().roomKey(roomKey).createdAt(LocalDateTime.now()).build();

        roomRepository.save(newRoom);

        Room room = roomRepository.findByRoomKey(roomKey);

        logger.info(room.toString());
    }

    @Test
    public void constraintRedundantRoomKeyTest() {
        Room room1 = Room.builder().roomKey(roomKey).createdAt(LocalDateTime.now()).build();
        Room room2 = Room.builder().roomKey(roomKey).createdAt(LocalDateTime.now()).build();

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            roomRepository.save(room1);
            roomRepository.save(room2); // 제약 조건 예외 예상.
        });
    }

    @BeforeEach
    public void deleteTestRoom() {
        Room room = roomRepository.findByRoomKey(roomKey);
        if(room!=null) roomRepository.delete(room);
    }
}
