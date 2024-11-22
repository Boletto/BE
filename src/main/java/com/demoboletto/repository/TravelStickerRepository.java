package com.demoboletto.repository;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.TravelSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelStickerRepository extends JpaRepository<TravelSticker, Long>, TravelStickerCustomRepository {
    List<TravelSticker> findAllByTravel(Travel travel);
}
