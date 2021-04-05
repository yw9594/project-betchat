package com.study.loge.betchat.repository;

import com.study.loge.betchat.model.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Room Table에 접근하기 위한 클래스입니다.
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByRoomKey(String roomKey);
}
