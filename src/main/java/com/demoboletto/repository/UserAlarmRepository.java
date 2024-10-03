package com.demoboletto.repository;

import com.demoboletto.domain.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {

    List<UserAlarm> findByUserId(Long id);
}
