package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysFrameRepository extends JpaRepository<SysFrame, Long> {
    Optional<SysFrame> findByFrameCode(String frameCode);
}
