package com.study.loge.betchat.service;

import com.study.loge.betchat.entity.Participate;
import com.study.loge.betchat.entity.Room;
import com.study.loge.betchat.entity.User;
import com.study.loge.betchat.exception.SubscribeException;
import com.study.loge.betchat.repository.ParticipateRepository;
import com.study.loge.betchat.repository.RoomRepository;
import com.study.loge.betchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 유저의 채팅방 참가 및 퇴장 이벤트를 처리하는 로직입니다.
@AllArgsConstructor
@Service
public class UserParticipateExitService {
    private UserRepository userReposotory;
    private RoomRepository roomRepository;
    private ParticipateRepository participateRepository;

    // 유저가 채팅방에 참가하는 행위를 처리합니다.
    public void processParticipate(Message<?> message) throws SubscribeException {
        StompHeaderAccessor stompHeaderAccessor = (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);

        String userKey = stompHeaderAccessor.getNativeHeader("user_key").get(0);
        String roomKey = stompHeaderAccessor.getNativeHeader("room_key").get(0);
        String simpSessionId = stompHeaderAccessor.getSessionId();

        User user = userReposotory.findByUserKey(userKey);
        Room room = roomRepository.findByRoomKey(roomKey);

        // 유효하지 않은 user 또는 room일 경우 예외를 발생시킵니다.
        if(user==null || room==null)
            throw new SubscribeException("유효하지 않은 user key 또는 room key입니다.");

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

    // 유저가 채팅방에서 나가는 행위를 처리합니다.
    public void processExit(Message<?> message) {
        StompHeaderAccessor stompHeaderAccessor = (StompHeaderAccessor) StompHeaderAccessor.getAccessor(message);
        String simpSessionId = stompHeaderAccessor.getSessionId();

        Participate participate = participateRepository.findBySimpSessionId(simpSessionId);
        participate.setIsJoined(0);
        participate.setExitedAt(LocalDateTime.now());

        participateRepository.save(participate);
    }
}
