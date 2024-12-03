package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.type.EFrameType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "user_frame", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_frame", columnNames = {"user_id", "frame_id", "custom_frame_id"})
})
public class UserFrame extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_frame_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    private SysFrame sysFrame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_frame_id")
    private UserCustomFrame customFrame;

    @Enumerated(EnumType.STRING)
    @Column(name = "frame_type", nullable = false)
    private EFrameType frameType;

    @Builder
    public UserFrame(User user, SysFrame sysFrame, UserCustomFrame customFrame, EFrameType frameType) {
        this.user = user;
        this.sysFrame = sysFrame;
        this.customFrame = customFrame;
        this.frameType = frameType;
    }

    public String getFrameCode() {
        return frameType == EFrameType.SYSTEM ? sysFrame.getFrameCode() : customFrame.getFrameCode();
    }
}
