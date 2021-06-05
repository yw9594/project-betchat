package com.study.loge.betchat.service.dao;

import com.study.loge.betchat.model.entity.Participate;
import com.study.loge.betchat.model.entity.Room;
import com.study.loge.betchat.model.entity.User;
import com.study.loge.betchat.repository.ParticipateRepository;
import com.study.loge.betchat.repository.RoomRepository;
import com.study.loge.betchat.repository.UserRepository;
import com.study.loge.betchat.utils.parser.StompHeaderParser;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Participate 테이블 Dao입니다.
@Component
@AllArgsConstructor
public class ParticipateDao {
    private UserRepository userReposotory;
    private RoomRepository roomRepository;
    private ParticipateRepository participateRepository;

    // 채팅방 참가를 저장합니다.
    public void create(Message<?> message) {
        String userKey = StompHeaderParser.getUserKey(message);
        String roomKey = StompHeaderParser.getRoomKey(message);
        String simpSessionId = StompHeaderParser.getSimpSessionId(message);

        User user = userReposotory.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        // 유저가 채팅방에 참가한 것을 DB에 저장합니다.
        Participate participate = Participate.builder()
                .user(user)
                .room(room)
                .isJoined(1)
                .joinedAt(LocalDateTime.now())
                .simpSessionId(simpSessionId)
                .build();

        participateRepository.save(participate);
    }

    // 채팅방 퇴장 요청에 따른 데이터를 갱신합니다.
    public void update(Message<?> message) {
        String simpSessionId = StompHeaderParser.getSimpSessionId(message);

        Participate participate = participateRepository.findBySimpSessionId(simpSessionId);
        participate.setIsJoined(0);
        participate.setExitedAt(LocalDateTime.now());

        participateRepository.save(participate);
    }
}
