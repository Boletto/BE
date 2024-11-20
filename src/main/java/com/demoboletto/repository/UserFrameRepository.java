package com.demoboletto.repository;

import com.demoboletto.domain.UserFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFrameRepository extends JpaRepository<UserFrame, Long> {
}
