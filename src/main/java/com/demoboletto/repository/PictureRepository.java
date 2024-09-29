package com.demoboletto.repository;

import com.demoboletto.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Query("select p from Picture p where p.travel.travelId = :id")
    List<Picture> findAllByTravelId(Long id);

    List<Picture> findByUserId(Long userId);

    List<Picture> findAllByTravel_TravelIdAndIsDeletedFalse(Long travelId);
}
