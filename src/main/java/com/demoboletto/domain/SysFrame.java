package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
