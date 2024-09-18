package com.demoboletto.repository;

import com.demoboletto.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

    List<Collect> findByUserIdAndFrameTypeIsNotNull(Long userId);

    List<Collect> findByUserIdAndStickerTypeIsNotNull(Long userId);
}