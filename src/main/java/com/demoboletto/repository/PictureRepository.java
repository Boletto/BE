package com.demoboletto.repository;

import com.demoboletto.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long>, PictureCustomRepository {
}