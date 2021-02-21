package com.study.loge.betchat.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// joined entity를 표현하기 위한 entity 클래스입니다.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Joined {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime joinedAt;
    private LocalDateTime chattedAt;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    @Override
    public String toString() {
        return "Joined{" +
                "id=" + id +
                ", joinedAt=" + joinedAt +
                ", chattedAt=" + chattedAt +
                '}';
    }
}
