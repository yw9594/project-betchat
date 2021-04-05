package com.study.loge.betchat.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// Participate Entity를 표현하기 위한 Entity 클래스입니다.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Participate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime joinedAt;
    private LocalDateTime exitedAt;
    private String simpSessionId;
    private int isJoined;

    // Participate Table은 log를 저장하므로, FK에 대해 optional=false입니다.
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Room.class, optional = false)
    @JoinColumn(name="room_id")
    private Room room;

    @Override
    public String toString() {
        return "Participate{" +
                "id=" + id +
                ", joinedAt=" + joinedAt +
                ", exitedAt=" + exitedAt +
                ", simpSessionId='" + simpSessionId + '\'' +
                ", isJoined=" + isJoined +
                ", user=" + user +
                ", room=" + room +
                '}';
    }
}
