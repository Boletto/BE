package com.demoboletto.domain;

import com.demoboletto.domain.common.Frame;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_custom_frame")
@NoArgsConstructor
@Getter
public class UserCustomFrame extends Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frame_seq")
    @SequenceGenerator(name = "frame_seq", sequenceName = "frame_seq", allocationSize = 1)
    @Column(name = "frame_id")
    private Long frameId;


    @Builder
    public UserCustomFrame(String frameUrl) {
        this.frameUrl = frameUrl;
    }

    public void setFrameCode() {
        this.frameCode = "CUS" + this.frameId;
    }
}
