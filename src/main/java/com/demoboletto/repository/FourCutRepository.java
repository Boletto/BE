package com.demoboletto.repository;

import com.demoboletto.domain.FourCut;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface FourCutRepository extends JpaRepository<FourCut, Long> {
    void deleteByTravel_TravelIdAndPictureIdx(Long travelId, int pictureIdx);
}
