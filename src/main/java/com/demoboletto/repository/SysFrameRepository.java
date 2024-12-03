package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.common.Frame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysFrameRepository extends JpaRepository<SysFrame, Long>, SysFrameCustomRepository {
    Optional<SysFrame> findByFrameCode(String frameCode);

    Optional<Frame> findFrameByFrameCode(String frameCode);
}
