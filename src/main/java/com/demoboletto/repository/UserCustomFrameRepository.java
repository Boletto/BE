package com.demoboletto.repository;

import com.demoboletto.domain.UserCustomFrame;
import com.demoboletto.domain.common.Frame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserCustomFrameRepository extends JpaRepository<UserCustomFrame, Long> {
    Optional<Frame> findFrameByFrameCode(String frameCode);
}
