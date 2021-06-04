package com.study.loge.betchat.monitor;

import com.study.loge.betchat.utils.exception.RoomEntranceCounterException;
import com.study.loge.betchat.utils.parser.StompHeaderParser;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

// 채팅방 출입을 검사합니다.
@Component
public class RoomEntranceCounter {
    private Map<String, String> participants = new TreeMap<>();           // 어느 참가자(sessionId)가 어느 채팅방(roomKey)에 참가하고 있는지 저장합니다.
    private Map<String, Integer> participantsCounter = new TreeMap<>();   // 어느 채팅방에(roomKey) 몇 명이 참가하고 있는지 저장합니다.
    private Set<String> disabledRoom = new TreeSet<>();                   // 비활성화된 채팅방의 roomKey를 저장합니다.

    // 채팅방 참가 요청을 처리합니다.
    public synchronized void participate(Message<?> message) throws RoomEntranceCounterException {
        String simpSessionId = StompHeaderParser.getSimpSessionId(message);
        String roomKey = StompHeaderParser.getRoomKey(message);

        // 비활성화 채팅방 목록에 roomKey가 존재한다면 예외를 발생시킵니다.
        if(disabledRoom.contains(roomKey))
            throw new RoomEntranceCounterException();
        // 참가수를 1 증가시킵니다.
        else {
            Integer count = participantsCounter.get(roomKey);
            if(count==null) count = 1;
            else count++;

            participantsCounter.put(roomKey, count);
            participants.put(simpSessionId, roomKey);
        }
    }

    // 채팅방 퇴장 요청을 처리합니다.
    public synchronized void exit(String simpSessionId){
        String roomKey = participants.remove(simpSessionId);

        // DISCONNECT가 STOMP 프로토콜에 의해 2회 이상 발생할 수 있습니다. 따라서 null일 경우 count를 감소시키지 않습니다.
        if(roomKey!=null) {
            Integer count = participantsCounter.get(roomKey);
            count--;

            // count가 0일 경우, 비활성화 방 리스트에 삽입합니다.
            if (count == 0) {
                participantsCounter.remove(roomKey);
                disabledRoom.add(roomKey);
            }
            else
                participantsCounter.put(roomKey, count);
        }
    }

    // 활성화된 채팅방 수를 반환합니다.
    public Integer getActiveRoomCount(){
        return participantsCounter.size();
    }
}
