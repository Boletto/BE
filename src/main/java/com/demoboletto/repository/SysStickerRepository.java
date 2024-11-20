package com.demoboletto.repository;

import com.demoboletto.domain.SysSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysStickerRepository extends JpaRepository<SysSticker, Long> {
    
}
