package com.demoboletto.repository;

import com.demoboletto.domain.SysSticker;

import java.util.List;

public interface SysStickerCustomRepository {
    List<SysSticker> findSystemStickers();

    List<SysSticker> findEventStickers();

    List<SysSticker> findDefaultStickers();

}
