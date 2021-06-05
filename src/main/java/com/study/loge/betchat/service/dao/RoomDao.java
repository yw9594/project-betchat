package com.study.loge.betchat.service.dao;

import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.repository.RoomRepository;
import com.study.loge.betchat.utils.KeyGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Room 테이블 Dao입니다.
@Component
@AllArgsConstructor
public class RoomDao {
    private final KeyGenerator keyGenerator;
    private final RoomRepository roomRepository;

    // 채팅방을 데이터베이스에 저장하고 UserKey를 저장합니다.
    public String create(){
        String roomKey = keyGenerator.generateKey();

        Room room = Room.builder()
                .roomKey(roomKey)
                .createdAt(LocalDateTime.now())
                .build();

        roomRepository.save(room);

        return roomKey;
    }
}
