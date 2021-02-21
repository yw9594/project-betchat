package com.study.loge.betchat.entity;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// user entity를 표현하기 위한 entity 클래스입니다.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userKey;
    private Integer activated;
    private LocalDateTime createdAt;

//    @ManyToOne(targetEntity = Joined.class, optional = false)
//    @JoinColumn(name="joined_id")
//    private Joined joined;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userKey='" + userKey + '\'' +
                ", activated='" + activated + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
