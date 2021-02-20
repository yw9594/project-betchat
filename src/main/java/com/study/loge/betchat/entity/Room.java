package com.study.loge.betchat.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

// room entity를 표현하기 위한 entity 클래스입니다.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomKey;
    private Integer activated;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomKey='" + roomKey + '\'' +
                ", activated=" + activated +
                ", createdAt=" + createdAt +
                '}';
    }
}
