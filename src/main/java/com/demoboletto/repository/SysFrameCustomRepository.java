package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;

import java.util.List;

public interface SysFrameCustomRepository {
    List<SysFrame> findDefaultFrames();
}
