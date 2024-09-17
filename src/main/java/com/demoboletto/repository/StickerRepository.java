package com.demoboletto.repository;

import com.demoboletto.domain.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {
    @Query("select s from Sticker s where s.travel.travelId = :id")
    List<Sticker> findAllByTravelId(Long id);
    @Query("delete from Sticker s where s.travel.travelId = :travelId")
    void deleteAllByTravelId(Long travelId);
}
