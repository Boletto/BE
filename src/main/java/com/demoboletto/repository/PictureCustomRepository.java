package com.demoboletto.repository;

public interface PictureCustomRepository {
    void detachPicturesByUserId(Long userId);
}
