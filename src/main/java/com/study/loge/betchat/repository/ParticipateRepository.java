package com.study.loge.betchat.repository;

import com.study.loge.betchat.model.entity.Participate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Participate Table에 접근하기 위한 클래스입니다.
@Repository
public interface ParticipateRepository extends CrudRepository<Participate, Long> {
    Participate findBySimpSessionId(String simpSessionId);

}
