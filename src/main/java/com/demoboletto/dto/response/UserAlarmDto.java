package com.demoboletto.dto.response;

import com.demoboletto.domain.UserAlarm;
import com.demoboletto.type.EAlarmType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class UserAlarmDto {
    @JsonProperty("user_alarm_id")
    @Schema(description = "user alarm unique id", example = "1234")
    private Long id;

    @Enumerated(EnumType.STRING)
    @JsonProperty("alarm_type")
    @Schema(description = "alarm type", example = "STICKER_ACQUISITION")
    private EAlarmType alarmType;

    @JsonProperty("message")
    @Schema(description = "alarm message", example = "{value} 스티커를 획득했어요.")
    private String message;

    @JsonProperty("read")
    @Schema(description = "is read default = false", example = "false")
    private boolean read;

    @JsonProperty("created_date")
    @Schema(description = "created date", example = "2021-08-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    public static UserAlarmDto of(UserAlarm alarm) {
        return UserAlarmDto.builder()
                .id(alarm.getId())
                .message(alarm.getAlarmType().getTemplate().replace("{value}", alarm.getValue()))
                .createdAt(alarm.getCreatedDate())
                .read(alarm.getIsRead())
                .alarmType(alarm.getAlarmType())
                .build();
    }
}
