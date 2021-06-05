package com.study.loge.betchat.monitor;

import com.study.loge.betchat.service.dao.ParticipateDao;
import com.study.loge.betchat.utils.validation.message.ParticipateValidationChecker;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

// 유저의 채팅방 참가 및 퇴장 이벤트를 처리하는 로직입니다.
@AllArgsConstructor
@Component
public class RoomEntranceManager {
    private ParticipateDao participateDao;
    private RoomEntranceCounter roomEntranceCounter;
    private ParticipateValidationChecker participateValidationChecker;

    // 유저가 채팅방에 참가하는 행위를 처리합니다.
    public void processParticipate(Message<?> message) throws Exception {
        // 참가 요청의 유효성을 검사합니다.
        participateValidationChecker.check(message);

        // 채팅방 참가를 카운팅합니다.
        roomEntranceCounter.participate(message);

        // 데이터베이스에 참가 기록을 저장합니다
        participateDao.create(message);
    }

    // 유저가 채팅방에서 나가는 행위를 처리합니다.
    public void processExit(Message<?> message) {
        // 채팅방 퇴장을 카운팅합니다.
        roomEntranceCounter.exit(message);

        // 채팅방 퇴장 기록을 저장합니다.
        participateDao.update(message);
    }
}
