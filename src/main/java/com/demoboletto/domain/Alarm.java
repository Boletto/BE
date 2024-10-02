package com.demoboletto.domain;
import com.demoboletto.type.EAlarmType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private EAlarmType alarmType;

    @Column(name = "message")
    private String message;

    @Builder
    public Alarm(EAlarmType alarmType, String message) {
        this.alarmType = alarmType;
        this.message = message;
    }
}
