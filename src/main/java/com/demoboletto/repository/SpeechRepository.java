package com.demoboletto.repository;

import com.demoboletto.domain.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {
    @Query("select s from Speech s where s.travel.travelId = :id")
    List<Speech> findAllByTravelId(Long id);
    @Query("delete from Speech s where s.travel.travelId = :travelId")
    void deleteAllByTravelId(Long travelId);

}
