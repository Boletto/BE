package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "user_alarm")
public class UserAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Alarm alarmTemplate;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    public UserAlarm(User user, Alarm alarmTemplate) {
        this.user = user;
        this.alarmTemplate = alarmTemplate;
        this.createdDate = LocalDateTime.now();
    }

    // 알림 읽음 처리
    public void markAsRead() {
        this.isRead = true;
    }
}

