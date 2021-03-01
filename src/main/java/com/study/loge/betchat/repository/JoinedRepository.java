package com.study.loge.betchat.repository;

import com.study.loge.betchat.entity.Joined;
import com.study.loge.betchat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// joined table에 접근하기 위한 repository 클래스입니다.
@Repository
public interface JoinedRepository extends CrudRepository<Joined, Long> {
    Joined findBySimpSessionId(String simpSessionId);

}
