//package com.demoboletto.domain;
//
//import com.demoboletto.type.EAlarmType;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
//@Table(name = "alarm_template")
////TODO: Change Entity name to AlarmTemplate
//public class AlarmTemplate {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "alarm_id")
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "alarm_type")
////    private EAlarmType alarmType;
////
//    @Builder
//    public AlarmTemplate(EAlarmType alarmType) {
//        this.alarmType = alarmType;
//    }
//
//}
