package com.demoboletto.domain;

import com.demoboletto.type.EAlarmType;
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

    @Column(name = "alarm_type")
    private EAlarmType alarmType;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "alarm_value")
    private String value;

    @Builder
    public UserAlarm(User user, EAlarmType alarmType, String value) {
        this.user = user;
        this.alarmType = alarmType;
        this.value = value;
        this.createdDate = LocalDateTime.now();
    }

    // 알림 읽음 처리
    public void markAsRead() {
        this.isRead = true;
    }
}

