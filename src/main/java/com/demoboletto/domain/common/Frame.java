package com.demoboletto.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class Frame extends BaseTimeEntity {

    @Column(name = "frame_code", unique = true)
    protected String frameCode;

    @Column(name = "frame_url")
    protected String frameUrl;

    public Frame(String frameCode, String frameUrl) {
        this.frameCode = frameCode;
        this.frameUrl = frameUrl;
    }
}