package com.demoboletto.repository;

import com.demoboletto.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : demo-boletto
 * @name : AnalysisRepository
 * @date : 2025. 4. 21. 오후 4:15
 * @modifyed : $
 **/
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    
}
