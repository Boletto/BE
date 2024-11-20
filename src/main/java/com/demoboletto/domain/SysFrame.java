package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "frame_code")
    private String frameCode;

    @Column(name = "frame_name")
    private String frameName;

    @Column(name = "frame_url")
    private String frameUrl;

    @Column(name = "description")
    private String description;

}
