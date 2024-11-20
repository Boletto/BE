package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_frame")
@NoArgsConstructor
@Getter
public class SysFrame extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frame_id")
    private Long frameId;

    @Column(name = "frame_code", unique = true)
    private String frameCode;

    @Column(name = "frame_name")
    private String frameName;

    @Column(name = "frame_url")
    private String frameUrl;

    @Column(name = "default_provided")
    private boolean defaultProvided;

    @Column(name = "description")
    private String description;

    @Builder
    public SysFrame(String frameCode, String frameName, String frameUrl, boolean defaultProvided, String description) {
        this.frameCode = frameCode;
        this.frameName = frameName;
        this.frameUrl = frameUrl;
        this.defaultProvided = defaultProvided;
        this.description = description;
    }
}
