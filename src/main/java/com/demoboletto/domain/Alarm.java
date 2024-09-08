package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=true)
    private User user;

    @Column(name = "contents")
    private String contents;

    @Column(name="alarm_status")
    private Boolean alarmStatus;

    @Builder
    public Alarm(User user, String contents, Boolean alarmStatus) {
        this.user = user;
        this.contents = contents;
        this.alarmStatus = alarmStatus;
    }
}
