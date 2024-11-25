package com.demoboletto.repository;

import com.demoboletto.domain.UserFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFrameRepository extends JpaRepository<UserFrame, Long>, UserFrameCustomRepository {
    List<UserFrame> findAllByUserId(Long userId);
}
