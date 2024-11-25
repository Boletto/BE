package com.demoboletto.repository;

import com.demoboletto.domain.UserSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStickerRepository extends JpaRepository<UserSticker, Long> {
    List<UserSticker> findAllByUserId(Long userId);
}
