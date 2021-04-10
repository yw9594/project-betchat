package com.study.loge.betchat.monitor;

import com.study.loge.betchat.utils.exception.SubscribeException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

// 채팅방 출입을 검사합니다.
@Component
public class RoomEntranceCounter {
    private Map<String, String> participants = new TreeMap<>();           // 어느 참가자(sessionId)가 어느 채팅방(roomKey)에 참가하고 있는지 저장합니다.
    private Map<String, Integer> participantsCounter = new TreeMap<>();   // 어느 채팅방에(roomKey) 몇 명이 참가하고 있는지 저장합니다.

    // 채팅방 참가 요청을 처리합니다.
    public synchronized void participate(String simpSessionId, String roomKey) throws SubscribeException {
        Integer count = participantsCounter.get(roomKey);

        if(count==null) count = 1;                                                            // 처음 생성된 채팅방에 대해 처리합니다.
        else if(count==0) throw new SubscribeException("참가할 수 없는 방입니다.");           // 채팅방에 아무도 존재하지 않는다면 비활성화된 채팅방입니다.
        else count++;                                                                         // 채팅방이 존재한다면 카운트를 증가시킵니다.

        participantsCounter.put(roomKey, count);
        participants.put(simpSessionId, roomKey);
    }

    // 채팅방 퇴장 요청을 처리합니다.
    public synchronized void exit(String simpSessionId){
        String roomKey = participants.remove(simpSessionId);

        // DISCONNECT가 의도와 달리 STOMP 프로토콜에 의해 2회 이상 발생할 수 있습니다. 따라서 null일 경우 count를 감소시키지 않습니다.
        if(roomKey!=null) {
            Integer count = participantsCounter.get(roomKey);
            count--;
            participantsCounter.put(roomKey, count);
        }
    }
}
