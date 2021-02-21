package com.study.loge.betchat.component;

import com.study.loge.betchat.entity.Room;
import com.study.loge.betchat.exception.RoomJoinException;
import com.study.loge.betchat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// 클라이언트의 request가 Controller 또는 Broker에 도달하기 전, 필요한 처리를 수행하는 클래스입니다.
@AllArgsConstructor
@Component
public class StompInboundChannelInterceptor implements ChannelInterceptor {
    private final RoomRepository roomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 유효하지 않은 room key subscribe를 제한합니다.
        StompCommand stompCommand = (StompCommand) message.getHeaders().get("stompCommand");
        if(stompCommand.equals(StompCommand.SUBSCRIBE)) {
            try{
                String roomKey = getRoomKey(message);
                checkRoomExist(roomKey);
            }
            catch (RoomJoinException e) {
                // 유효하지 않은 채팅방 참가일 경우, Message를 처리하지 않습니다.
                message=null;
            }
            finally {
                return message;
            }
        }
        // subscribe가 아닐 경우, 정상 처리합니다.
        else
            return message;
    }

    // Message의 Header에서 roomKey를 찾아 반환합니다.
    private String getRoomKey(Message<?> message) {
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>)message.getHeaders().get("nativeHeaders");
        List<String> destination = nativeHeaders.get("destination");
        String [] splitedDestination = destination.get(0).split("/");
        String roomKey = splitedDestination[splitedDestination.length-1];

        return roomKey;
    }

    // 채팅방 참가의 유효성을 검사합니다.
    private void checkRoomExist(String roomKey) throws RoomJoinException {
        Room room = roomRepository.findByRoomKey(roomKey);

        if(room==null || room.getActivated()==0)
            throw new RoomJoinException();
    }
}
