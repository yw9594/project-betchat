package com.study.loge.betchat.repository;

import com.study.loge.betchat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// User Table에 접근하기 위한 repository 클래스입니다.
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserKey(String userKey);
}
