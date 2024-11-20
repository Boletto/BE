package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysFrameRepository extends JpaRepository<SysFrame, Long> {
}
