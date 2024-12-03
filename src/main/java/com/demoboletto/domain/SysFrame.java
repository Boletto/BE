package com.demoboletto.domain;

import com.demoboletto.domain.common.Frame;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_frame")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
public class SysFrame extends Frame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frame_id")
    private Long frameId;


    @Column(name = "frame_name")
    private String frameName;


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
